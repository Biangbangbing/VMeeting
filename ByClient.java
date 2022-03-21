package net.by0119;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ByClient {
    //private Socket socket = null;
    String ip;
    int port;
    ByUser localUser;
    ByUser targetUser ;

    public void setByUser(ByUser localUser) {
        this.localUser = localUser;
    }

    JFrame clientUI ;
    Socket socket;

    private ByClientReceiveMsg byClientReceiveMsg = new ByClientReceiveMsg();

    public ByClientReceiveMsg getByClientReceiveMsg() {
        return this.byClientReceiveMsg;
    }

    public Socket getSocket() {
        return socket;
    }

    public JFrame getClientUI() {
        return clientUI;
    }

    public void setClientUI(JFrame clientUI) {
        this.clientUI = clientUI;
    }

    public void setLocalUser(ByUser localUser) {
        this.localUser = localUser;
    }

    public void setTargetUser(ByUser targetUser) {
        this.targetUser = targetUser;
    }

    public ByUser getLocalUser() {
        return localUser;
    }

    public ByUser getTargetUser() {
        return targetUser;
    }

    public ByClient(String ip, int port) throws IOException {
        //先连接服务器以保证正常通信
        System.out.println("启动客户端。");
        this.socket = connect(ip,port);

        //登录页面——获取用户信息
        new ByClientLoginUI().login(this);

        //收消息端登录成功之后会初始化client界面

        //收消息线程启动，其实本身这个客户端就是一个线程，while收消息，但是界面初始化有可能被抢占执行没有初始化完毕，所以另开了一个线程=>待解释
        byClientReceiveMsg.setSocket(this.socket);
        byClientReceiveMsg.setByClient(this);
//        byClientReceiveMsg.setClientUI(this.clientUI);

//        clientUI = new ByClientUI().initUI("Client-0",socket,name,ID,byClientReceiveMsg);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {
                while(true) {
                    try {
                        byClientReceiveMsg.receiveMsg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
    public Socket connect(String ip , int port) throws IOException {
        Socket socket = new Socket(ip,port);
        return socket;
    }
}
