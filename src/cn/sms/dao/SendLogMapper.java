package cn.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sms.model.MsgPort;
import cn.sms.model.PageInfoCS;
import cn.sms.model.SendLog;

public interface SendLogMapper {

    Integer testSelect();
    
    //List<SendLog> findMsg(@Param("phoneNum")String phoneNum);
    
    Integer addMsg(@Param("sendLog")SendLog sendLog);
    
    List<PageInfoCS> findAllMsg();
    
    List<PageInfoCS> findAllMsgByNum(@Param("start")int start, @Param("pageSize")int pageSize);
    
    List<MsgPort> findMsg(@Param("phoneNum")String phoneNum);
    
    List<PageInfoCS> findResultMsg(@Param("phoneNum")String phoneNum);
    
    List<PageInfoCS> findTimeMsg(@Param("sendTimeSelect")String sendTimeSelect,@Param("endTimeSelect")String endTimeSelect);
    
}
