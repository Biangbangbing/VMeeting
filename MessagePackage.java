package net.by0119;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 属性：
 *  自己的ID,name，目标ID，日期，消息类型，消息内容
 * 方法：
 *  1.发送消息包：
 *
 *  2.接收消息包
 */
public class MessagePackage {
    int localID;
    String localName;
    int targetID;
    String dateStr;
    int messageType;
    public MessagePackage(){

    }

    public MessagePackage(int localID, String localName, int targetID, int messageType) {
        this.localID = localID;
        this.localName = localName;
        this.targetID = targetID;
        this.messageType = messageType;
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = dateFormat.format(date);
        System.out.println("时间长度"+date.toString());
    }

    public MessagePackage(int localID, int targetID, int messageType) {
        this.localID = localID;
        this.targetID = targetID;
        this.messageType = messageType;
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = dateFormat.format(date);
        System.out.println("时间长度"+date.toString());
    }

//    public void sendMessHeader(OutputStream outputStream){
//        IOMsg_Str ioMsg_str = new IOMsg_Str();
//        IOMsg_INT ioMsg_int = new IOMsg_INT();
//        try {
//            ioMsg_int.sendInt(outputStream,this.localID);
//            ioMsg_str.SendStr(outputStream,this.localName);
//            ioMsg_int.sendInt(outputStream,targetID);
//            ioMsg_str.SendStr(outputStream,dateStr);
//            ioMsg_int.sendInt(outputStream,messageType);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void receiveMessHeader(InputStream inputStream){
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        IOMsg_INT ioMsg_int = new IOMsg_INT();
        try {
            this.localID = ioMsg_int.receiveInt(inputStream);
            this.localName = ioMsg_str.receiveStr(inputStream);
            this.targetID = ioMsg_int.receiveInt(inputStream);
            this.dateStr = ioMsg_str.receiveStr(inputStream);
            this.messageType = ioMsg_int.receiveInt(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessHeader2(OutputStream outputStream){
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        IOMsg_INT ioMsg_int = new IOMsg_INT();
        try {
            ioMsg_int.sendInt(outputStream,this.localID);
            ioMsg_int.sendInt(outputStream,targetID);
            ioMsg_str.SendStr(outputStream,dateStr);
            ioMsg_int.sendInt(outputStream,messageType);

            System.out.println("发送消息包。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessHeader2(InputStream inputStream){
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        IOMsg_INT ioMsg_int = new IOMsg_INT();
        try {
            System.out.println("收到消息包。");
            this.localID = ioMsg_int.receiveInt(inputStream);
            this.targetID = ioMsg_int.receiveInt(inputStream);
            this.dateStr = ioMsg_str.receiveStr(inputStream);
            this.messageType = ioMsg_int.receiveInt(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLocalID() {
        return localID;
    }

    public String getLocalName() {
        return localName;
    }

    public int getTargetID() {
        return targetID;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getDateStr() {
        return dateStr;
    }
}
