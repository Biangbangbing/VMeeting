package net.by0119;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ByServer {
    private ServerSocket serverSocket = null;
    private ByServerUI byServerUI = new ByServerUI();
    private ByServerReceiveMsg byServerReceiveMsg = new ByServerReceiveMsg();


    public ByServer(int port) throws IOException {
        Socket socket = connect(port);
        JFrame jFrame = byServerUI.initUI("Server",socket,"Server",0,byServerReceiveMsg);
        while(true)
            byServerReceiveMsg.receiveMsg(jFrame,socket);
    }

    //连接客户端，获得客户端socket
    public Socket connect(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Socket socket_client = serverSocket.accept();
        return socket_client;
    }
}
