package cn.sms.dao;

import org.apache.ibatis.annotations.Param;

import cn.sms.model.SavePhone;

public interface SavePhoneMapper {
	
	Integer addSavePhone(@Param("savePhone") SavePhone savePhone);
	
	int findSavePhone(String phoneNum);
    
}
