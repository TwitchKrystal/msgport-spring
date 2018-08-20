package cn.sms.controller;

import cn.sms.dao.SavePhoneMapper;
import cn.sms.model.PhoneEntity;
import cn.sms.model.SavePhone;
import com.alibaba.fastjson.JSONObject;
import cn.utils.excel.ImportExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/import")
public class ImportController {
	
	@Autowired
	private SavePhoneMapper savePhoneMapper;
    
    @RequestMapping("/uploadPhoneNum")
    public void uploadPhoneNum(Model model,HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile file) throws IOException{
    	
    	response.setContentType("text/html;charset=utf-8");//设置响应的编码格式，不然会出现中文乱码现象 
    	
    	SavePhone savePhone = new SavePhone();
    	
        PrintWriter out = response.getWriter(); 
    	try {
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PhoneEntity> list = ei.getDataList(PhoneEntity.class);
			for(int i = list.size()-1;i>0;i--){
				if(StringUtils.isBlank(list.get(i).getPhoneNum())){
					list.remove(i);
				}
			}
			model.addAttribute("phoneNums",list);
			for (int j = 0; j < list.size(); j++) {
				String phoneNum = list.get(j).getPhoneNum();
				
				int count = savePhoneMapper.findSavePhone(phoneNum);
				
				System.out.println("我是谁" + count);
				
				if(count == 0) {
					savePhone.setPhoneNum(list.get(j).getPhoneNum());
					savePhone.setImporttime(new Date());
					savePhoneMapper.addSavePhone(savePhone);
				} else {
					continue;
				}
				
				
			}
			
			JSONObject json = new JSONObject();
			json.put("phoneNums", list);
			out.println(json.toJSONString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

}
