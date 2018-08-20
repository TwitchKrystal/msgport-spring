package cn.sms.model;

public class SendMsg{

    private String phoneNum;
    
    private String msg;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "SendMsg [phoneNum=" + phoneNum + ", msg=" + msg + "]";
	}

	

}
