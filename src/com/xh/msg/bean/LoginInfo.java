package com.xh.msg.bean;

/**
 * 创建者：Start
 * 创建时间：2015/11/27 9:29
 */
public class LoginInfo {

    //登陆IP
    private String host;
    //端口，一般为7890
    private String port;
    //客户企业代码，如：901236
    private String sp_number;
    //特服号，如：09997
    private String source_addr;
    //口令密码，如：1236
    private String shared_secret;
    //业务代码
    private static String service_Id;
    //接收者显示手机号码
    private static String src_Terminal_Id;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSp_number() {
        return sp_number;
    }

    public void setSp_number(String sp_number) {
        this.sp_number = sp_number;
    }

    public String getSource_addr() {
        return source_addr;
    }

    public void setSource_addr(String source_addr) {
        this.source_addr = source_addr;
    }

    public String getShared_secret() {
        return shared_secret;
    }

    public void setShared_secret(String shared_secret) {
        this.shared_secret = shared_secret;
    }

    public static String getService_Id() {
        return service_Id;
    }

    public static void setService_Id(String service_Id) {
        LoginInfo.service_Id = service_Id;
    }

    public static String getSrc_Terminal_Id() {
        return src_Terminal_Id;
    }

    public static void setSrc_Terminal_Id(String src_Terminal_Id) {
        LoginInfo.src_Terminal_Id = src_Terminal_Id;
    }
}
