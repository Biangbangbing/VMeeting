package net.by0119;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ByClient {
    private Socket socket = null;
    String ip;
    int port;
    String name;
    int ID;

    private ServerSocket serverSocket = null;
    private ByClientReceiveMsg byClientReceiveMsg = new ByClientReceiveMsg();

    public ByClient(String ip,int port) throws IOException {
        //登录获取个人信息和读取好友信息

        connect(ip,port);
        JFrame jFrame = new ByClientUI().initUI("Client-0",socket,"白敬亭",1015,byClientReceiveMsg);
        while(true)
            byClientReceiveMsg.receiveMsg(jFrame,socket);

    }

    public Socket connect(String ip , int port) throws IOException {
        socket = new Socket(ip,port);
        return socket;
    }
}
