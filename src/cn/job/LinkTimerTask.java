package cn.job;

import cn.sms.service.MessageProxy;
import com.xh.msg.bean.LoginInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimerTask;

public class LinkTimerTask extends TimerTask {

    private ServletConfig config;

    public LinkTimerTask(ServletConfig config){
        System.out.println("=======================初始化连接=======================");
        //连接
        this.config = config;
    }

    @Override
    public void run() {
        ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        MessageProxy messageProxy = (MessageProxy)appCtx.getBean("messageProxy");
        System.out.println("messageProxy="+messageProxy);
        Properties prop = new Properties();
        InputStream in = LinkTimerTask.class.getResourceAsStream("/conf/msg.properties");
        try {
            //配置参数
            prop.load(in);
            String host = prop.getProperty("msg.host").trim();
            String port = prop.getProperty("msg.port").trim();
            String sp_number = prop.getProperty("msg.sp_number").trim();
            String source_addr = prop.getProperty("msg.source_addr").trim();
            String shared_secret = prop.getProperty("msg.shared_secret").trim();
            String service_Id = prop.getProperty("msg.service_Id").trim();
            String src_Terminal_Id = prop.getProperty("msg.src_Terminal_Id").trim();
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setHost(host);
            loginInfo.setPort(port);
            loginInfo.setSp_number(sp_number);
            loginInfo.setSource_addr(source_addr);
            loginInfo.setShared_secret(shared_secret);
            loginInfo.setService_Id(service_Id);
            loginInfo.setSrc_Terminal_Id(src_Terminal_Id);
            messageProxy.login(loginInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
