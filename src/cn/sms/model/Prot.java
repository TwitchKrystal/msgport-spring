package cn.sms.model;

public class Prot {

	private String phoneNum;
	
	private String state;
	
	private String retime;
	
	private String importtime;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRetime() {
		return retime;
	}

	public void setRetime(String retime) {
		this.retime = retime;
	}

	public String getImporttime() {
		return importtime;
	}

	public void setImporttime(String importtime) {
		this.importtime = importtime;
	}

	@Override
	public String toString() {
		return "Prot [phoneNum=" + phoneNum + ", state=" + state + ", retime=" + retime + ", importtime=" + importtime
				+ "]";
	}
	
	
	
}
