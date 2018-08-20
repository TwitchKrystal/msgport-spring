package cn.sms.model;

import java.util.Date;

public class SendLog {

    private int theid;

    private String phoneNum;

    private String msgcontent;

    private Date receivetime;

    private String readed;

	public int getTheid() {
		return theid;
	}

	public void setTheid(int theid) {
		this.theid = theid;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMsgcontent() {
		return msgcontent;
	}

	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}

	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	public String getReaded() {
		return readed;
	}

	public void setReaded(String readed) {
		this.readed = readed;
	}

	@Override
	public String toString() {
		return "SendLog [theid=" + theid + ", phoneNum=" + phoneNum + ", msgcontent=" + msgcontent + ", receivetime="
				+ receivetime + ", readed=" + readed + "]";
	}



}
