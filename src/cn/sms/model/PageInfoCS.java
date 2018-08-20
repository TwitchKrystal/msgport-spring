package cn.sms.model;

public class PageInfoCS {
	
	private String phoneNum;
	
	private String sendTimeSelect;
	
	private String endTimeSelect;
	
	private String importtime;
	
	private String receivetime;
	
	private String readed;
	
	private String delivetime;
	
	private int pageNum;
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getSendTimeSelect() {
		return sendTimeSelect;
	}

	public void setSendTimeSelect(String sendTimeSelect) {
		this.sendTimeSelect = sendTimeSelect;
	}

	public String getEndTimeSelect() {
		return endTimeSelect;
	}

	public void setEndTimeSelect(String endTimeSelect) {
		this.endTimeSelect = endTimeSelect;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getImporttime() {
		return importtime;
	}

	public void setImporttime(String importtime) {
		this.importtime = importtime;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getReaded() {
		return readed;
	}

	public void setReaded(String readed) {
		this.readed = readed;
	}

	public String getDelivetime() {
		return delivetime;
	}

	public void setDelivetime(String delivetime) {
		this.delivetime = delivetime;
	}

	@Override
	public String toString() {
		return "PageInfoCS [phoneNum=" + phoneNum + ", sendTimeSelect=" + sendTimeSelect + ", endTimeSelect="
				+ endTimeSelect + ", importtime=" + importtime + ", receivetime=" + receivetime + ", readed=" + readed
				+ ", delivetime=" + delivetime + ", pageNum=" + pageNum + "]";
	}

	
}
