package cn.sms.service;

import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.xh.msg.bean.LoginInfo;
import com.xh.msg.bean.Message;

public interface MessageProxy {

    /**
     * 无返回值
     * @param loginInfo
     */
    public void login(LoginInfo loginInfo);

    /**
     * 检查是否登陆状态
     * @return
     */
    public boolean isLogin();

    /**
     * 设置登录状态
     * @param flag
     */
    public void setIsLogin(Boolean flag);


    public boolean send(Message message) throws PException;
    
    /**
     * 接收短信
     * @param msg
     */
    public void ProcessReceiveDeliverMsg(CMPPMessage msg);



}
