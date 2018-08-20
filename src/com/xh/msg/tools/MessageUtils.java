package com.xh.msg.tools;

import com.xh.msg.conf.MessageConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 创建者：Start
 * 创建时间：2015/12/4 9:34
 */
public class MessageUtils {


    /**
     * 拆分长短信
     * 为了接受者只接收到一条信息
     * 采用下列添加协议头的方式
     * @param message
     * @return
     */
    public static List<byte[]> getLongByte(String message){
        List<byte[]> list = new ArrayList<byte[]>();
        try {
            byte[] messageASCII;
            messageASCII = message.getBytes("ASCII");
            int messageASCIILen = messageASCII.length;// 长短信长度
            int maxMessageLen = 120;
            Integer inter = new Integer(new Random().nextInt());
//            byte msgSeq = inter.byteValue();
//            int messageUCS2Count = messageUCS2Len / (maxMessageLen - 6) + 1;//
            //长短信分为多少条发送
//            byte[] tp_udhiHead = new byte[6];
//            tp_udhiHead[0] = 0x05;//表示剩余协议头的长度
//            tp_udhiHead[1] = 0x00;
//            tp_udhiHead[2] = 0x03;//表示剩余协议头的长度
//            tp_udhiHead[3] = msgSeq;//同一条信息的值
//            tp_udhiHead[4] = (byte) messageUCS2Count;//总条数
//            tp_udhiHead[5] = 0x01;// 默认为第一条
//            for (int i = 0; i < messageUCS2Count;i++) {
//                tp_udhiHead[5] = (byte) (i + 1);
                byte[]msgContent;
//                if (i != messageUCS2Count - 1) {// 不为最后一条
//                    msgContent=byteAdd(tp_udhiHead,messageUCS2,i*(maxMessageLen-6),(i+1)*(maxMessageLen-6));
//                    list.add(msgContent);
//                    System.out.println(list);
//                } else {
//                    msgContent=byteAdd(tp_udhiHead,messageUCS2, i*(maxMessageLen-6),messageUCS2Len);
//                    list.add(msgContent);
//                    System.out.println(list);
//                }
                
                msgContent=messageASCII;
                list.add(msgContent);
                
//            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<byte[]> getShortByte(String message){
        List<byte[]> list = new ArrayList<byte[]>();
        try {
            byte[] messageUCS2;
            messageUCS2 = message.getBytes("UnicodeBigUnmarked");
            int messageASCIILen = messageUCS2.length;// 长短信长度
            int maxMessageLen = 120;
            Integer inter = new Integer(new Random().nextInt());
            byte[]msgContent;
            msgContent=messageUCS2;
            list.add(msgContent);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    

    // 判断一个字符是否是中文
     public static boolean isChinese(char c) {
         return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
     }
     // 判断一个字符串是否含有中文
     public static boolean isChinese(String str) {
         if (str == null) return false;
         for (char c : str.toCharArray()) {
             if (isChinese(c)) return true;// 有一个中文字符就返回
         }
         return false;
     }


    
    /**
     * 拼接字节
     * @param tp_udhiHead
     * @param messageASCII
     * @param i
     * @param j
     * @return
     */
    private static byte[] byteAdd(byte[] tp_udhiHead, byte[] messageASCII, int i,int j) {
        byte[] msg_Content = new byte[j-i+6];
        System.arraycopy(tp_udhiHead,0,msg_Content,0,6);
        System.arraycopy(messageASCII,i,msg_Content,6,j-i);
        return msg_Content;
    }
  

    /**
     * 拆分字符串
     * @param str
     * @return
     */
    public static List<String> getSubStringList(String str){
        int num = MessageConst.single_length;
        if(num<0){
            return null;
        }
        List<String> stringList = new ArrayList<String>();
        for(int i=0;i<str.length();i+=num){
            //截取70位，如果最后长度小于70，取全部，否则取70位。
            String subStr = str.substring(i,str.length()-i<num?str.length():i+num);
            stringList.add(subStr);
        }
        return stringList;
    }

    /**
     * 字节长度
     * @param s
     * @return
     */
    public static int getWordCount(String s){
        int length = 0;
        for(int i=0;i<s.length();i++){
            int ascii = Character.codePointAt(s,i);
            if(ascii>=0&&ascii<=255){
                length++;
            }else{
                length +=2;
            }
        }
        return length;
    }
}
