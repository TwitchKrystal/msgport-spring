package cn.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sms.model.SaveResp;

public interface SaveRespMapper {
	
	Integer addRespMsg(@Param("saveResp") SaveResp saveResp);
	
//    List<SaveResp> findRespMsg(@Param("number") String number);

}
