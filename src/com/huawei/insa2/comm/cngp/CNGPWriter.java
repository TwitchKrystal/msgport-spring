package com.huawei.insa2.comm.cngp;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PWriter;
import com.huawei.insa2.comm.cngp.message.CNGPMessage;

import java.io.IOException;
import java.io.OutputStream;

public class CNGPWriter extends PWriter{

  /** 解码后消息输出到该输出流。*/
  protected OutputStream out;

  /**
   * 构造CNGP协议编码器。
   */
   public CNGPWriter(OutputStream out) {
       this.out = out;
   }

  /**
   * 将CNGP消息编码并写入输出流中。
   * @param message 待编码的消息对象。
   */
   public void write(PMessage message) throws IOException {
     out.write(((CNGPMessage) message).getBytes());
   }

  /**
   * 发送心跳信息。
   */
   public void writeHeartbeat() throws IOException {
      // out.write(HEARTBEAT);
   }
}
