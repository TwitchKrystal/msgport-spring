package com.xh.msg.tools;

import cn.sms.service.MessageProxy;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.Args;
import com.huawei.smproxy.SMProxy30;

public class MySMProxy30 extends SMProxy30 {

    private MessageProxy iMessageProxy;

    public MySMProxy30(MessageProxy iMessageProxy, Args args){
        super(args);
        this.iMessageProxy = null;
        this.iMessageProxy = iMessageProxy;
    }

    public CMPPMessage onDeliver(CMPP30DeliverMessage msg){
        if(iMessageProxy!=null){
            iMessageProxy.ProcessReceiveDeliverMsg(msg);
        }
        return super.onDeliver(msg);
    }

    /**
     * 终止
     */
/*    public void OnTerminate(){
        iMessageProxy.Terminate();
    }*/


}