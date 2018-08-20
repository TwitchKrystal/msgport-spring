package cn.sms.model;

import java.util.Date;

public class SaveResp {
	
	private int id;
	
	private String number;
	
	private String msg;
	
	private Date ti;

	public Date getTi() {
		return ti;
	}

	public void setTi(Date ti) {
		this.ti = ti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "SaveResp [id=" + id + ", number=" + number + ", msg=" + msg + ", ti=" + ti + "]";
	}

}
