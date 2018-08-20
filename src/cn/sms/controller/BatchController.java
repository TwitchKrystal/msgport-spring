package cn.sms.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.xh.msg.bean.Message;

import cn.sms.dao.SavePhoneMapper;
import cn.sms.dao.SendLogMapper;
import cn.sms.model.PhoneEntity;
import cn.sms.model.SavePhone;
import cn.sms.model.SendLog;
import cn.sms.model.SendMsg;
import cn.sms.service.MessageProxy;
import cn.utils.FileOperation;
import cn.utils.excel.ImportExcel;

@Controller
@RequestMapping("/batch")
public class BatchController {

	@Autowired
	private MessageProxy messageProxy;
	    
	@Autowired
	private SendLogMapper sendLogMapper;
	
	@Autowired
	private SavePhoneMapper savePhoneMapper;
	
	@RequestMapping("/message")
	public void sendMsg(@RequestParam("msg") String msg,HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile file) throws IOException {

		SavePhone savePhone = new SavePhone();
		
		try {
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<PhoneEntity> list = ei.getDataList(PhoneEntity.class);
			for(int i = list.size()-1;i>0;i--){
				if(StringUtils.isBlank(list.get(i).getPhoneNum())){
					list.remove(i);
				}
			}
			
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
			
			String[] strings = new String[list.size()];
			
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getPhoneNum());
				strings[i] = list.get(i).getPhoneNum();
			}
			
			PrintWriter pw = null;

	        response.setCharacterEncoding("UTF-8");
	        pw = response.getWriter();
	        String result = null;
	        String info = null;
	        String res = null;
	        System.out.println("messageProxy=" + messageProxy);

	        Message message = new Message();
	        //String num = request.getParameter("num");
	        //String msg = msg;//request.getParameter("msg");
	        //String ch[] = num.split(",");
	        SendLog sendlog = new SendLog();
	        for (int i = 0; i < strings.length; i++) {
	            System.out.println(list.size());
	            boolean flag0 = true;
	            if (strings[i] == null || "".equals(strings[i])) {
	                result = "ERROR";
	                res = "错误";
	                info = "NUM IS NOT NULL!";
	                flag0 = false;
	            }
	            if (msg == null || "".equals(msg)) {
	                result = "ERROR";
	                res = "错误";
	                info = "MSG IS NOT NULL!";
	                flag0 = false;
	            }
	            if (flag0) {
	                boolean flag = messageProxy.isLogin();

	                if (flag) {
	                    System.out.println("=========================测试发送短信，登陆成功！=========================");
	                    message.setPhoneNumber(strings[i]);
	                    message.setMsgContent(msg);
	                    boolean bool = messageProxy.send(message);
	                    //messageProxy.ProcessReceiveDeliverMsg(null);
	                    if (bool) {
	                        System.out.println("=========================测试发送短信，发送成功！=========================");
	                        result = "SUCCESS";
	                        res = "成功";
	                        info = "SEND SUCCESSFUL!";
	                        this.write(message);
	                    } else {
	                        System.out.println("=========================测试发送短信，发送失败！=========================");
	                        result = "FAIL";
	                        res = "失败";
	                        info = "SEND FAIL!";
	                        messageProxy.setIsLogin(false);
	                    }
	                    
	                    sendlog.setPhoneNum(strings[i]);
	                    sendlog.setMsgcontent(msg);
	                    sendlog.setReaded(res);
	                    sendlog.setReceivetime(new Date());
	                	sendLogMapper.addMsg(sendlog);
	                    
	                    JSONObject json = new JSONObject();
	                    json.put("STATUS",result);
	                    json.put("INFO",info);
	                    pw.write(json.toJSONString());
	                }
	                pw.flush();
	                pw.close();
	                }
	                       
	            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        
	}
	
	
	private void write(Message message) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        String str = sdf.format(date);
        String dir = System.getProperty("msgport.root");
        dir = dir + File.separator + "WEB-INF" + File.separator + "message_send_logs" + File.separator + str + ".txt";
        File file = new File(dir);
        try {
            FileOperation.createFile(file);
            Date date1 = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String date_str = sdf1.format(date1);
            FileOperation.appendTxtFile("发送时间：" + date_str + ";发送号码：" + message.getPhoneNumber() + ";发送内容" + message.getMsgContent() + "\n", file);
            System.out.println(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
