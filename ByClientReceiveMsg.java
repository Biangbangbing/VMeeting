package net.by0119;

import net.by0114.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ByClientReceiveMsg {
//    private JTextArea dialogTrack;
    private JTextPane dialogTrack;

    private JFrame clientUI ;
    private JFrame clientLoginUI ;
    private JFrame clientRegisterUI;
    private JFrame videoUI;

    private Socket socket;
    private ByClient byClient;

    private ByUser localUser;
    private ByUser targetUser;

    private ByClientUI byClientUI;

    public ByClient getByClient() {
        return byClient;
    }

//    public void setDialogTrack(JTextArea dialogTrack) {
//        this.dialogTrack = dialogTrack;
//    }
    public void setDialogTrack(JTextPane dialogTrack) {
        this.dialogTrack = dialogTrack;
    }

    public void setByClientUI(ByClientUI byClientUI) {
        this.byClientUI = byClientUI;
    }
    public void setClientLoginUI(JFrame clientLoginUI) {
        this.clientLoginUI = clientLoginUI;
    }
    public void setClientRegisterUI(JFrame clientRegisterUI) {
        this.clientRegisterUI = clientRegisterUI;
    }

    public void setLocalUser(ByUser byUser) {
        this.localUser = byUser;
    }
    public void setTargetUser(ByUser targetUser) {
        this.targetUser = targetUser;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public void setByClient(ByClient byClient) {
        this.byClient = byClient;
    }

    public void receiveMsg() throws IOException {
        System.out.println("客户端接收到消息");
        InputStream inputStream = this.socket.getInputStream();
        IOMsg_INT ioMsg_int = new IOMsg_INT();
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        IOMsg_Image ioMsg_image = new IOMsg_Image();

        MessagePackage receiveMessPackage = new MessagePackage();
        receiveMessPackage.receiveMessHeader2(inputStream);

        int sendID = receiveMessPackage.getLocalID();
        int messageType = receiveMessPackage.getMessageType();

        String time = receiveMessPackage.getDateStr();
        System.out.println("messageType:"+messageType);
        System.out.println("消息的目的id："+receiveMessPackage.getTargetID());
        System.out.println("当前客户端对应用户id："+localUser.getID());
        if ((receiveMessPackage.getTargetID() != localUser.getID())) {
            System.out.println("消息接收错误，不是发给我的消息");
            return;
        }

        switch (messageType) {
            case 1: { //文字
                String msg = ioMsg_str.receiveStr(inputStream);
//                dialogTrack.append("\n" + time + "\n" + sendID + " : " + msg);
                dialogTrack.setText(dialogTrack.getText() + "\n" + time + "\n" + sendID + " : " + msg);
                break;
            }
            case 2:{  //直线
                int X1 = ioMsg_int.receiveInt(inputStream);
                int Y1 = ioMsg_int.receiveInt(inputStream);
                int X2 = ioMsg_int.receiveInt(inputStream);
                int Y2 = ioMsg_int.receiveInt(inputStream);
                Graphics pen = this.clientUI.getGraphics();
//                pen.drawLine(X1,Y1,X2,Y2);
//                dialogTrack.append("\n" + time + "\n" + sendID + " : 发送了涂鸦消息。");
                dialogTrack.setText(dialogTrack.getText() + "\n" + time + "\n" + sendID + " : 发送了涂鸦消息。");
                break;
            }
            case 10:{
                BufferedImage image = ioMsg_image.receiveImage(inputStream);
                dialogTrack.setText(dialogTrack.getText() + "\n" + time + "\n" + sendID + " :\n");
                ImageIcon icon = new ImageIcon(image);
                dialogTrack.insertIcon(icon);
                break;
            }
            case 12:{
                JFrame videoUI = this.byClientUI.videoUI(this.socket,this);
                dialogTrack.setText(dialogTrack.getText() + "\n" + time + "\n" + sendID + " : 发起视频通话。\n");
                break;
            }
            case 254:{
                System.out.println("进入注册判断的case");
                int stateCode = inputStream.read();
                System.out.println("stateCode:"+stateCode);
                if(stateCode == 1) {
                    System.out.println(this.localUser.getName()+" ，注册成功，欢迎加入BY·Chatting！");
                    this.clientRegisterUI.setVisible(false);
                    this.clientLoginUI.setVisible(true);
                    //this.clientUI = new ByClientUI(localUser).initUI("Client-0",this.socket,this);

                    //是否要紧接着加接收离线消息
                }else{
                    System.out.println("ID或密码错误，登陆失败。");
                    JOptionPane.showMessageDialog(null,"ID或密码错误，登陆失败。","登陆失败",JOptionPane.YES_OPTION);
                }
                break;
            }
            case 255:{ //登录操作
                int stateCode = inputStream.read();
                if(stateCode == 1) {
                    this.localUser.setSocket(this.socket);   //绑定当前客户端
                    System.out.println(this.localUser.getID()+" ，登录成功，欢迎回来！");
                    this.clientLoginUI.setVisible(false);
                    this.clientUI = new ByClientUI(localUser).initUI("Client-0",this.socket,this);


                    //是否要紧接着加接收离线消息
                }else if(stateCode == 2){
                    System.out.println("用户已在线，异常登录，登录失败。");
                    JOptionPane.showMessageDialog(null,"用户已在线，异常登录，登录失败。","登陆异常",JOptionPane.YES_OPTION);

                }
                else{
                    System.out.println("ID或密码错误，登陆失败。");
                    JOptionPane.showMessageDialog(null,"ID或密码错误，登陆失败。","登陆失败",JOptionPane.YES_OPTION);
                }
                break;
            }
        }


    }

}
