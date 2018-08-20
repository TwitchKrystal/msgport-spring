package cn.sms.model;

import com.xh.msg.bean.Message;

public class ReceiveMessage extends Message{

    private String theId;

    public String getTheId() {
        return theId;
    }

    public void setTheId(String theId) {
        this.theId = theId;
    }

}
