package net.by0119;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 消息报：0-255
 * 1 文字消息
 *
 *
 *
 */

public class ByServerReceiveMsg extends Thread{

    private JTextArea dialogTrack;
    private ArrayList<ByUser> userList;
    private ArrayList<MessageBuffer> bufferList;


    private JFrame serverUI;
    private Socket socket;    //获取客户端连接服务器端的socket 服务器端对客户端的读写流都从此获取


    public void setDialogTrack(JTextArea dialogTrack) {
        this.dialogTrack = dialogTrack;
    }

    public void setUserList(ArrayList<ByUser> userList) {
        this.userList = userList;
    }

    public void setBufferList(ArrayList<MessageBuffer> bufferList) {
        this.bufferList = bufferList;
    }

    public void setServerUI(JFrame serverUI) {
        this.serverUI = serverUI;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void receiveMsg(JFrame jFrame, Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        IOMsg_INT ioMsg_int = new IOMsg_INT();
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        IOMsg_Image ioMsg_image = new IOMsg_Image();

        MessagePackage receiveMessPackage = new MessagePackage();
        receiveMessPackage.receiveMessHeader2(inputStream);
        System.out.println("服务器收到消息");

        int sendID = receiveMessPackage.getLocalID();
//        String sendName = receiveMessPackage.getLocalName();
        int targetID = receiveMessPackage.getTargetID();
        int messageType = receiveMessPackage.getMessageType();
        String time = receiveMessPackage.getDateStr();

        switch (messageType) {
            case 1: { //文字
                String msg = ioMsg_str.receiveStr(inputStream);
                boolean flag = false;
                for (int i=0;i<userList.size();i++){
                    System.out.println(userList.get(i).getID()+" "+userList.get(i).getName()+" "+userList.get(i).getPassword());
                    if(userList.get(i).getID() == targetID && userList.get(i).getState()==1) {   //在线则转发给这个客户端
                        OutputStream outputStream = userList.get(i).getSocket().getOutputStream();
                        MessagePackage messagePackage = receiveMessPackage;
                        messagePackage.sendMessHeader2(outputStream);

                        ioMsg_str.SendStr(outputStream, msg);
                        flag = true;
                        break;
                    }
                }
                if (flag==true) {
                    dialogTrack.append("\n" + time + "\nFrom:" + sendID + "   To: " + targetID + "   ======>发送成功\n" + msg + "\n");

                }else{   //不在线保存到缓存中
                    dialogTrack.append("\n" + time + "\nFrom:" + sendID + "   To: "+ targetID +"   ======>已缓存，待上线发送\n"+ msg+"\n");
                    MessageBuffer messageBuffer = new MessageBuffer(receiveMessPackage);
                    messageBuffer.setMsg(msg);
                    bufferList.add(messageBuffer);
                }
                break;
            }
//            case 2:{  //直线
//                int X1 = ioMsg_int.receiveInt(inputStream);
//                int Y1 = ioMsg_int.receiveInt(inputStream);
//                int X2 = ioMsg_int.receiveInt(inputStream);
//                int Y2 = ioMsg_int.receiveInt(inputStream);
//                Graphics pen = jFrame.getGraphics();
//                pen.drawLine(X1,Y1,X2,Y2);
////                dialogTrack.append("\n" + time + "\n" + sendName + " : 发送了涂鸦消息。");
//                break;
//            }
            case 10:{
                System.out.println("发送图片信息");
                BufferedImage image = ioMsg_image.receiveImage(inputStream);
                boolean flag = false;
                for (int i=0;i<userList.size();i++){
                    System.out.println(userList.get(i).getID()+" "+userList.get(i).getName()+" "+userList.get(i).getPassword());
                    if(userList.get(i).getID() == targetID && userList.get(i).getState()==1) {   //在线则转发给这个客户端
                        OutputStream outputStream = userList.get(i).getSocket().getOutputStream();
                        MessagePackage messagePackage = receiveMessPackage;
                        messagePackage.sendMessHeader2(outputStream);

                        ioMsg_image.sendImage(outputStream,image);
//                        ioMsg_str.SendStr(outputStream, msg);
                        flag = true;
                        break;
                    }
                }
                if (flag==true) {
                    dialogTrack.append("\n" + time + "\nFrom:" + sendID + "   To: " + targetID + "   ======>发送成功\n 图片信息" + "\n");

                }else{   //不在线保存到缓存中
                    dialogTrack.append("\n" + time + "\nFrom:" + sendID + "   To: "+ targetID +"   ======>已缓存，待上线发送\n 图片信息"+ "\n");
                    MessageBuffer messageBuffer = new MessageBuffer(receiveMessPackage);
                    messageBuffer.setImage(image);
                    bufferList.add(messageBuffer);
                }
                break;
            }
            case 12:{
                System.out.println("发送视频通话请求");
                Queue<BufferedImage> img = new LinkedList<>();
                BufferedImage image = ioMsg_image.receiveImage(inputStream);
                boolean flag = false;
                for (int i=0;i<userList.size();i++){
                    System.out.println(userList.get(i).getID()+" "+userList.get(i).getName()+" "+userList.get(i).getPassword());
                    if(userList.get(i).getID() == targetID && userList.get(i).getState()==1) {   //在线则转发给这个客户端
                        OutputStream outputStream = userList.get(i).getSocket().getOutputStream();
                        MessagePackage messagePackage = receiveMessPackage;
                        messagePackage.sendMessHeader2(outputStream);

                        ioMsg_image.sendImage(outputStream,image);
//                        ioMsg_str.SendStr(outputStream, msg);
                        flag = true;
                        break;
                    }
                }
                if (flag==true) {
                    dialogTrack.append("\n" + time + "\nFrom:" + sendID + "   To: " + targetID + "   ======>发送成功\n 图片信息" + "\n");

                }else{   //不在线保存到缓存中
                    dialogTrack.append("\n" + time + "\nFrom:" + sendID + "   To: "+ targetID +"   ======>已缓存，待上线发送\n 图片信息"+ "\n");
                    MessageBuffer messageBuffer = new MessageBuffer(receiveMessPackage);
                    messageBuffer.setImage(image);
                    bufferList.add(messageBuffer);
                }
                break;
            }
            case 253:{
                System.out.println("收到离线请求。");
                for(int i=0;i<userList.size();i++){
                    if(userList.get(i).getID() == sendID){
                        userList.get(i).setState(0);
                        dialogTrack.append("\n" + time + "\nFrom:" + sendID + "  ======>已离线\n");
                        break;
                    }
                }
                break;
            }

            case 254:{
                System.out.println("收到注册请求。");
                String getName = ioMsg_str.receiveStr(inputStream);
                int getID = ioMsg_int.receiveInt(inputStream);
                String getPassword =ioMsg_str.receiveStr(inputStream);
                OutputStream outputStream = socket.getOutputStream();

                MessagePackage sendMessPackage = new MessagePackage(0,getID,254);
                sendMessPackage.sendMessHeader2(outputStream);
                boolean flag = false;
                for(int i=0;i<userList.size();i++){
                    if(userList.get(i).getID() == getID) {
                        outputStream.write(0);  //用户重复
                        flag = true;
                        break;
                    }
                }
                if(flag==false) {
                    outputStream.write(1);      //注册成功
                    userList.add(new ByUser(getName,getID,getPassword));
                }
                break;
            }
            case 255:{  //
                //String getName = ioMsg_str.receiveStr(inputStream);
                System.out.println("收到登录请求。");
                int getID = ioMsg_int.receiveInt(inputStream);
                String getPassword =ioMsg_str.receiveStr(inputStream);
                System.out.println("请求的id账号："+getID);

                OutputStream outputStream = socket.getOutputStream();
                int localID = 0;
                MessagePackage sendMessPackage = new MessagePackage(0,getID,255);
                int flag = 0;
                ByUser nowUser = null;
                for(int i=0;i<userList.size();i++){
                    if(userList.get(i).getID() == getID && userList.get(i).getPassword().equals(getPassword)) {
                        if(userList.get(i).getState()==1){
                            flag = 2;
                            sendMessPackage.sendMessHeader2(outputStream);
                            outputStream.write(2);
                            System.out.println("用户已在线，异常登录，登录失败。");
                            break;
                        }

                        sendMessPackage.sendMessHeader2(outputStream);
                        outputStream.write(1);

                        userList.get(i).setState(1);
                        userList.get(i).setSocket(this.socket);
                        userList.get(i).setIp(this.socket.getInetAddress().getAddress().toString());
                        userList.get(i).setPort(this.socket.getPort());
                        nowUser = userList.get(i);
                        System.out.println("允许登录。");

                        dialogTrack.append("\n" + time + "\nFrom:" + sendID + "  ======>已上线\n");
                        flag = 1;
                        break;
                    }
                }

                for(int i=0;i<bufferList.size();i++){  //查看是否有离线消息发送给这个要登录的客户端
                    if(bufferList.get(i).getMessagePackage().getTargetID() == nowUser.getID() && flag==1){
                        bufferList.get(i).getMessagePackage().sendMessHeader2(outputStream);
                        ioMsg_str.SendStr(outputStream,bufferList.get(i).getMsg());
                        bufferList.remove(i);
                    }
                }

                if(flag==0) {
                    sendMessPackage.sendMessHeader2(outputStream);
                    outputStream.write(0);
                    System.out.println("ID或密码错误，登录失败。");
                }
                break;
            }
        }
//        if(messageType==1) {
//            String msg = ioMsg_str.receiveStr(inputStream);
//            dialogTrack.append("\n" + time + "\n" + sendName + " : " + msg);
//        }
//        else if(messageType == 2){
//            int X1 = ioMsg_int.receiveInt(inputStream);
//            int Y1 = ioMsg_int.receiveInt(inputStream);
//            int X2 = ioMsg_int.receiveInt(inputStream);
//            int Y2 = ioMsg_int.receiveInt(inputStream);
//            Graphics pen = jFrame.getGraphics();
//            pen.drawLine(X1,Y1,X2,Y2);
//            dialogTrack.append("\n" + time + "\n" + sendName + " : 发送了涂鸦消息。");
//        }



    }

    @Override
    public void run() {
        try {
            while(true) {
                this.receiveMsg(this.serverUI, this.socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}