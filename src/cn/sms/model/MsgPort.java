package cn.sms.model;

import java.util.Date;

public class MsgPort {
	
	
	private String phoneNum;
	
	private String msgcontent;
	
	private String time;
	
	private String stute;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStute() {
		return stute;
	}

	public void setStute(String stute) {
		this.stute = stute;
	}

	@Override
	public String toString() {
		return "MsgPort [phoneNum=" + phoneNum + ", msgcontent=" + msgcontent + ", time=" + time + ", stute=" + stute
				+ "]";
	}
	

}
