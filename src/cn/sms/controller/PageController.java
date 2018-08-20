package cn.sms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.sms.dao.SendLogMapper;
import cn.sms.model.PageInfoCS;

@Controller
@RequestMapping("/page")
public class PageController {

	@Autowired
	private SendLogMapper sendLogMapper;
	
	@RequestMapping("/info")
	@ResponseBody
	public PageInfo<PageInfoCS> page(@RequestParam("pageNum") int pageNum, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象 
		
		//PrintWriter out = response.getWriter(); 
		PageInfo<PageInfoCS> pageInfo = null;
		
		PageHelper.startPage(Integer.valueOf(pageNum),
                10);

		List<PageInfoCS> li = sendLogMapper.findAllMsg();

		pageInfo = new PageInfo<PageInfoCS>(li,10);
		
//		JSONObject json = new JSONObject();
//		json.put("phoneNums", li);
//		out.println(json.toJSONString());
		
		pageInfo.setList(sendLogMapper.findAllMsgByNum((pageNum-1)*10,10));
		
		return pageInfo;
		
	}
	
}
