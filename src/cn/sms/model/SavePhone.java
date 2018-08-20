package cn.sms.model;

import java.util.Date;

public class SavePhone {
	
	private int num;
	
	private String phoneNum;
	
	private Date importtime;

	public String getPhoneNum() {
		return phoneNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Date getImporttime() {
		return importtime;
	}

	public void setImporttime(Date importtime) {
		this.importtime = importtime;
	}

	@Override
	public String toString() {
		return "SavePhone [num=" + num + ", phoneNum=" + phoneNum + ", importtime=" + importtime + "]";
	}

	
	
	

}
