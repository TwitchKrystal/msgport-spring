package cn.job;

import cn.sms.service.MessageProxy;
import com.xh.msg.bean.LoginInfo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CheckLoginJobs implements Job {

    private ApplicationContext appCtx;

    private static String APPLICATION_CONTEXT_KEY = "applicationContext";

    private MessageProxy messageProxy;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("=======================检测登录任务=======================");
        try {
            appCtx = (ApplicationContext)jobExecutionContext.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        this.messageProxy = (MessageProxy)appCtx.getBean("messageProxy");
        boolean flag = messageProxy.isLogin();
        if (flag) {
            System.out.println("=========================短信用户登陆成功=========================");
        }else {
            this.reLogin();
        }
    }

    /**
     * 重新登录
     */
    private void reLogin(){
        System.out.println("=======================短信用户未登陆,重新登录！=======================");
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
            messageProxy.setIsLogin(false);
            messageProxy.login(loginInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
