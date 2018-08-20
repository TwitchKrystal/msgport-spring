package cn.job;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Timer;

public final class StartUpServlet extends HttpServlet{

    //短信重连时间间隔1000为1秒，60000为1分钟，300000为5分钟，600000为10分钟，1800000为30分钟，3600000为1小时
    public final static long SECOND = 3600000;

    /**
     * 任务入口
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("=======================启动任务=======================");
        Timer timer = new Timer();
        timer.schedule(new LinkTimerTask(config),0,SECOND);
    }

}
