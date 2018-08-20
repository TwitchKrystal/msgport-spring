package com.xh.msg.conf;

import java.util.Date;

public interface MessageConst {
    
    public static final int single_length = 100;//每条短信长度
    public static final int registered_Delivery = 0;
    public static final int msg_Level = 0;
    public static final int fee_UserType = 0;
    public static final String fee_Terminal_Id = "";
    public static final int fee_Terminal_Type = 0;
    public static final int tp_Pid = 0;
    public static final int tp_Udhi = 0;//长消息是1.短消息是0
    public static final int msg_Fmt_ASCII = 0;//长短信ASCII
    public static final int msg_Fmt_UCS2 = 8;//长短信UCS2
    //public static final int msg_Fmt = 15;//逐条发送GBK
    public static final String fee_Type = "01";
    public static final String fee_Code = "0";
    public static final Date valid_Time = null;
    public static final Date at_Time = null;
    public static final String reserve = "0";

}