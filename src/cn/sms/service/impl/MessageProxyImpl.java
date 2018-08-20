package cn.sms.service.impl;

import cn.sms.dao.SaveRespMapper;
import cn.sms.model.ReceiveMessage;
import cn.sms.model.SaveResp;
import cn.sms.service.MessageProxy;
import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitRepMessage;
import com.huawei.insa2.util.Args;
import com.xh.msg.bean.LoginInfo;
import com.xh.msg.bean.Message;
import com.xh.msg.conf.MessageConst;
import com.xh.msg.tools.MessageUtils;
import com.xh.msg.tools.MySMProxy30;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("messageProxy")
public class MessageProxyImpl implements MessageProxy,MessageConst {

    /**
     * 短信端口信息配置
     */
    private static Args args;

    private static LoginInfo loginInfo;

    /**
     * 短信代理
     */
    private MySMProxy30 smp;

    /**
     * 端口登陆状态
     */
    private boolean loginSmProxy;

    //接收短信编码
    private static String RETURN_UNICODE_SC = "Unicode";
    private static String RETURN_UNICODE_TEST = "ASCII";
    private int pk_Total = 1;//短信总条数
    private int pk_Number = 1;//第几条短信
    //业务代码
    private String service_Id;
    //特服号，如：09997
    private String msg_Src;
    //接收者显示手机号码
    private String src_Terminal_Id;
    private String dest_Terminal_Id[];
    private int dest_Terminal_Type = 0;
    private byte msg_Content[];//短信内容字节
    private Integer nowThreadNum = 0;//检测接收短信进程
    private Integer maxThreadNum = 5;

    public void login(LoginInfo info){
        if(loginSmProxy) {
            System.out.println("系统已经初始化...");
            return;
        }
        args = new Args();
        args.set("host",info.getHost().trim());
        args.set("port",info.getPort().trim());
        args.set("sp-number",info.getSp_number().trim());
        args.set("source-addr",info.getSp_number().trim());
        args.set("shared-secret",info.getShared_secret().trim());
        args.set("heartbeat-interval","30");
        args.set("reconnect-interval","60");
        args.set("heartbeat-noresponseout","5");
        args.set("transaction-timeout","60");
        args.set("version","0x30");
        args.set("debug","false");
        loginInfo = info;
        try {
            System.out.println("===================正在登录短信端口==================");
            smp = new MySMProxy30(this, args);
            System.out.println("===================登录短信端口成功==================");
            loginSmProxy = true;
            /**
             * 启动接收程序进程
             */
            do{
                if(nowThreadNum<maxThreadNum){
                    Thread thread = new MoniterThread();
                    thread.setName("daemon-thread-" + nowThreadNum);
                	thread.start();
                    nowThreadNum++;
                }
                Thread.sleep(10000L);
            }while (true);
        } catch(Exception ex) {
            System.out.println("====================登录短信端口失败:"+ex.getMessage()+"====================");
            ex.printStackTrace();
        }
    }


    /**
     * 是否登陆
     * @return
     */
    public boolean isLogin(){
        return loginSmProxy;
    }

    /**
     * 设置登录状态
     * @param flag
     */
    public void setIsLogin(Boolean flag){
        this.loginSmProxy = flag;
    }

    /**
     * 监测仪
     */
    class MoniterThread extends Thread {
        public void run(){
            String connState;
            try{
                do{
                    connState = smp.getConnState();
                    if(connState == null) {
                        //"系统运行正常";
                    }
                    Thread.sleep(10000L);
                } while(true);
            }catch(Exception e){
                maxThreadNum--;
                System.out.println("==================状态监控线程出现异常退出！===================");
                e.printStackTrace();
                return;
            }
        }
        public MoniterThread() {
            setDaemon(true);
        }
    }



    @Override
    public boolean send(Message message)throws PException{
        if(!loginSmProxy) {
            System.out.println("===============系统没有成功登录，发送失败！！=================");
            return false;
        }
        try{
            service_Id = loginInfo.getService_Id();
            msg_Src = loginInfo.getSource_addr();
            src_Terminal_Id = loginInfo.getSrc_Terminal_Id();
            dest_Terminal_Id = new String[1];//发送到一个号码
            dest_Terminal_Id[0] = String.valueOf(message.getPhoneNumber());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            /**
             * 长短信，接受者显示一条
             */
            System.out.println(message.getMsgContent().trim());
            boolean chinese = MessageUtils.isChinese(message.getMsgContent().trim());
            System.out.println(chinese);
            boolean flag = false;
            if (chinese) {
            	List<byte[]> list = MessageUtils.getShortByte(message.getMsgContent().trim());
            	pk_Total = 1;
                pk_Number = 1;
                msg_Content = list.get(0);
                flag = this.processSubmitRep(smp.send(this.getSubmitUCS2Msg()));
                return flag;
            } else {
            	List<byte[]> list = MessageUtils.getLongByte(message.getMsgContent().trim());
            	pk_Total = 1;
                pk_Number = 1;
                msg_Content = list.get(0);
                flag = this.processSubmitRep(smp.send(this.getSubmitMsg()));
                return flag;
            }
//            if(list!=null&&list.size()>0){
//                pk_Total = list.size();//总条数
//                System.out.println(pk_Total);
//                for(int i=0;i<list.size();i++){
//                    pk_Number = i+1;//第几条
//                    msg_Content = list.get(i);
//                    flag = this.processSubmitRep(smp.send(this.getSubmitMsg()));
//                }
//            }
            
            
        }catch(IllegalArgumentException ex) {
            ex.printStackTrace();
            return false;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    /**
     * 发送返回信息
     * @param msg
     */
    private boolean processSubmitRep(CMPPMessage msg) throws PException {
        CMPP30SubmitRepMessage repMsg = (CMPP30SubmitRepMessage)msg;
        if(repMsg != null && repMsg.getResult() == 0){
            System.out.println("=========================发送成功！Result=" + repMsg.getResult() + ";SequenceId=" + repMsg.getSequenceId()+"=========================");
            return true;
        }else {
            System.out.println("=========================发送失败！Result=" + repMsg.getResult() + ";SequenceId=" + repMsg.getSequenceId()+"=========================");
            return false;
        }
    }

    /**
     * 生成短信bean
     * @return
     */
    private CMPP30SubmitMessage getSubmitMsg(){
        try{
        	int msg_Fmt = msg_Fmt_ASCII;
            CMPP30SubmitMessage CMPP30Submitmessage = new CMPP30SubmitMessage(pk_Total, pk_Number, registered_Delivery, msg_Level, service_Id, fee_UserType, fee_Terminal_Id,fee_Terminal_Type, tp_Pid, tp_Udhi, msg_Fmt, msg_Src, fee_Type, fee_Code, valid_Time, at_Time, src_Terminal_Id,dest_Terminal_Id,dest_Terminal_Type,msg_Content,reserve);
            return CMPP30Submitmessage;
        }catch(IllegalArgumentException e){
            System.out.println("提交短消息请求的输入参数不合法");
            e.printStackTrace();
            CMPP30SubmitMessage CMPP30SubmitMessage = null;
            return CMPP30SubmitMessage;
        }catch(Exception e){
            System.out.println("提交短消息请求处理异常");
            e.printStackTrace();
            CMPP30SubmitMessage CMPP30SubmitMessage = null;
            return CMPP30SubmitMessage;
        }
    }

    private CMPP30SubmitMessage getSubmitUCS2Msg(){
        try{
        	int msg_Fmt = msg_Fmt_UCS2;
            CMPP30SubmitMessage CMPP30Submitmessage = new CMPP30SubmitMessage(pk_Total, pk_Number, registered_Delivery, msg_Level, service_Id, fee_UserType, fee_Terminal_Id,fee_Terminal_Type, tp_Pid, tp_Udhi, msg_Fmt, msg_Src, fee_Type, fee_Code, valid_Time, at_Time, src_Terminal_Id,dest_Terminal_Id,dest_Terminal_Type,msg_Content,reserve);
            return CMPP30Submitmessage;
        }catch(IllegalArgumentException e){
            System.out.println("提交短消息请求的输入参数不合法");
            e.printStackTrace();
            CMPP30SubmitMessage CMPP30SubmitMessage = null;
            return CMPP30SubmitMessage;
        }catch(Exception e){
            System.out.println("提交短消息请求处理异常");
            e.printStackTrace();
            CMPP30SubmitMessage CMPP30SubmitMessage = null;
            return CMPP30SubmitMessage;
        }
    }
    
    @Autowired
    private SaveRespMapper saveRespMapper;

    @Override
    public void ProcessReceiveDeliverMsg(CMPPMessage msg) {
    	SaveResp respMsg = new SaveResp();
        CMPP30DeliverMessage deliverMsg = (CMPP30DeliverMessage)msg;
        if(deliverMsg.getRegisteredDeliver() == 0){
            try{
                ReceiveMessage receiveMessage = new ReceiveMessage();
                    System.out.println("接收消息: 数据传输字节="+deliverMsg.getMsgFmt());
                    switch (deliverMsg.getMsgFmt()) {
					case 0:
						System.out.println("接收消息: 主叫号码="+deliverMsg.getSrcterminalId());
                        System.out.println("接收消息: 内容=" + new String(deliverMsg.getMsgContent(), RETURN_UNICODE_TEST));
                        respMsg.setNumber(deliverMsg.getSrcterminalId());
                        System.out.println(new String(deliverMsg.getMsgContent(), RETURN_UNICODE_TEST));
                        respMsg.setMsg(new String(deliverMsg.getMsgContent(), RETURN_UNICODE_TEST));
                        respMsg.setTi(new Date());
                        saveRespMapper.addRespMsg(respMsg);
                        receiveMessage.setPhoneNumber(deliverMsg.getSrcterminalId());
                        receiveMessage.setMsgContent(new String(deliverMsg.getMsgContent(), RETURN_UNICODE_TEST));
						break;
					case 8:
						System.out.println("接收消息: 主叫号码="+deliverMsg.getSrcterminalId());
	                    System.out.println("接收消息: 内容=" + new String(deliverMsg.getMsgContent(), RETURN_UNICODE_SC));
	                    respMsg.setNumber(deliverMsg.getSrcterminalId());
	                    System.out.println(new String(deliverMsg.getMsgContent(), RETURN_UNICODE_SC));
	                    respMsg.setMsg(new String(deliverMsg.getMsgContent(), RETURN_UNICODE_SC));
	                    respMsg.setTi(new Date());
	                    int a = saveRespMapper.addRespMsg(respMsg);
	                    System.out.println(a);
	                    receiveMessage.setPhoneNumber(deliverMsg.getSrcterminalId());
	                    receiveMessage.setMsgContent(new String(deliverMsg.getMsgContent(), RETURN_UNICODE_SC));
						break;
					}
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("收到状态报告消息： stat=" + deliverMsg.getStat());
        }
    }


}
