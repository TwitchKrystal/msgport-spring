package cn.sms.model;

import cn.utils.excel.annotation.ExcelField;

public class PhoneEntity {
	private String phoneNum;
	
	@ExcelField(title="电话号码",align=2,sort=10)
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "PhoneEntity [phoneNum=" + phoneNum + "]";
	}

}
