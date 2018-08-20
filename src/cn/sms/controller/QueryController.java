package cn.sms.controller;

import cn.sms.dao.SendLogMapper;
import cn.sms.model.MsgPort;
import cn.sms.model.PageInfoCS;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/query")

public class QueryController {

    @Autowired
    private SendLogMapper sendLogMapper;
    
    @RequestMapping("/selectMsg")
    public void selectccc(@RequestBody PageInfoCS pageInfoCS, HttpServletResponse response) throws IOException {
    	
    	response.setContentType("text/plain;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象 
		
		PrintWriter out = response.getWriter(); 
		
		String phoneNum = pageInfoCS.getPhoneNum();
		
		String sendTimeSelect = pageInfoCS.getSendTimeSelect();
		
		String endTimeSelect = pageInfoCS.getEndTimeSelect();
		
		int pageNum = pageInfoCS.getPageNum();
		
		PageInfo<PageInfoCS> page = null;
		
		if("".equals(phoneNum)) {
			
			PageHelper.startPage(Integer.valueOf(pageNum),
	                10);
			
			List<PageInfoCS> li = sendLogMapper.findTimeMsg(sendTimeSelect, endTimeSelect);

			page = new PageInfo(li);
			
			System.out.println(page);
			
			JSONObject json = new JSONObject();
			json.put("number", li);
			json.put("page", page);
			out.println(json.toJSONString());

			//pageInfo.setList(sendLogMapper.findTimeMsg(sendTimeSelect, endTimeSelect, (pageNum-1)*10, 10));
			
		} else if(!"".equals(phoneNum)){
			
			PageHelper.startPage(Integer.valueOf(pageNum),
	                10);
			
			List<PageInfoCS> pl = sendLogMapper.findResultMsg(phoneNum);

			page = new PageInfo(pl);
			
			System.out.println(page);
			
			JSONObject json = new JSONObject();
			json.put("number", pl);
			json.put("page", page);
			out.println(json.toJSONString());

			//pageInfo.setList(sendLogMapper.findTimeMsg(sendTimeSelect, endTimeSelect, (pageNum-1)*10, 10));
			
		}
		
		
     
    }
    

    
    @RequestMapping("/selectreport")
    public void queryMsg(@RequestBody MsgPort msgport, HttpServletResponse response) throws IOException{
 
    	response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象 
    	
        PrintWriter out = response.getWriter(); 
    	
    	String phoneNum = msgport.getPhoneNum();
    	
    	List<MsgPort> sl = sendLogMapper.findMsg(phoneNum);
    	
    	JSONObject json = new JSONObject();
    	
    	json.put("number", sl);
    	
    	out.println(json.toJSONString());
    	
    	System.out.println(sl);
    
    }

}
