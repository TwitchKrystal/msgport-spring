package cn.sms.controller;

import cn.sms.dao.SendLogMapper;
import cn.sms.model.SendLog;
import cn.sms.model.SendMsg;
import cn.sms.service.MessageProxy;
import cn.utils.FileOperation;
import com.alibaba.fastjson.JSONObject;
import com.huawei.insa2.comm.PException;
import com.xh.msg.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/alone")
public class SendController {

    @Autowired
    private MessageProxy messageProxy;

    @Autowired
    private SendLogMapper sendLogMapper;
    
    /**
     * 发送短信
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public void sendMsg(@RequestBody SendMsg sendMsg, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, PException {

        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");
            pw = response.getWriter();
            String result = null;
            String info = null;
            String res = null;
            System.out.println("messageProxy=" + messageProxy);

            Message message = new Message();
            String num = sendMsg.getPhoneNum();//request.getParameter("cardNum");
            String msg = sendMsg.getMsg();//request.getParameter("msg");
            message.setPhoneNumber(num);
            message.setMsgContent(msg);

            boolean flag0 = true;
            if(num==null||"".equals(num)){
                result = "ERROR";
                res = "错误";
                info = "NUM IS NOT NULL!";
                flag0 = false;
            }
            if(msg==null||"".equals(msg)){
                result = "ERROR";
                res = "错误";
                info = "MSG IS NOT NULL!";
                flag0 = false;
            }
            SendLog sendLog = new SendLog();
            if(flag0){
                boolean flag = messageProxy.isLogin();
                if (flag) {
                    System.out.println("=========================测试发送短信，登陆成功！=========================");
                    boolean bool = messageProxy.send(message);
                    System.out.println("=========================发送号码："+num+"=========================");
                    System.out.println("=========================短信内容："+msg+"=========================");
                    if (bool) {
                        System.out.println("=========================测试发送短信，发送成功！=========================");
                        result = "SUCCESS";
                        res = "成功";
                        info = "SEND SUCCESSFUL!";
                        this.write(message);
                    } else {
                        System.out.println("=========================测试发送短信，发送失败！=========================");
                        result = "FAIL";
                        res = "失败";
                        info = "SEND FAIL!";
                        messageProxy.setIsLogin(false);
                    }
                } else {
                    result = "ERROR";
                    res = "错误";
                    info = "LOGIN FAIL!";
                }
            }

            sendLog.setPhoneNum(num);
            sendLog.setMsgcontent(msg);
            sendLog.setReaded(res);
            sendLog.setReceivetime(new Date());
        	sendLogMapper.addMsg(sendLog);
            
            JSONObject json = new JSONObject();
            json.put("STATUS",result);
            json.put("INFO",info);
            json.toJSONString();
            pw.write(json.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
        pw.flush();
        pw.close();
    }


    private void write(Message message){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        String str = sdf.format(date);
        String dir = System.getProperty("msgport.root");
        dir = dir + File.separator + "WEB-INF" + File.separator + "message_send_logs" + File.separator + str+".txt";
        File file = new File(dir);
        try {
            FileOperation.createFile(file);
            Date date1 = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String date_str  = sdf1.format(date1);
            FileOperation.appendTxtFile("发送时间："+date_str+";发送号码："+message.getPhoneNumber()+";发送内容"+message.getMsgContent()+"\n",file);
            System.out.println(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
