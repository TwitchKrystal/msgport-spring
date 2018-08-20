package com.xh.msg.bean;

import java.util.Date;

/**
 * 创建者：Start
 * 创建时间：2015/11/27 9:26
 */
public class Message {
    
    //电话号码
    private String phoneNumber;

    //短信内容
    private String msgContent;

    //接收时间
    private Date receiveTime;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
