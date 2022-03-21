package net.by0119;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ByServer {
    private ServerSocket serverSocket = null;
    private ByServerUI byServerUI = new ByServerUI();

    ArrayList<ByUser> userList = new ArrayList<>();
    ArrayList<MessageBuffer> bufferList = new ArrayList<MessageBuffer>();

    public ByServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        getUsers();
        JFrame jFrame = byServerUI.initUI("Server",this.serverSocket,"Server",0);
        new Thread(){
            @Override
            public void run() {
                while(true) {
                    try {
                        ByServerReceiveMsg byServerReceiveMsg  = new ByServerReceiveMsg();
                        byServerReceiveMsg.setUserList(userList);
                        byServerReceiveMsg.setBufferList(bufferList);
                        Socket socket = connect();
                        System.out.println("socket:"+socket.getPort());
//                        byServerUI.setByServerReceiveMsg(byServerReceiveMsg);
                        byServerReceiveMsg.setDialogTrack(byServerUI.getShowDia());
                        byServerUI.setSocket(socket);             //不知道是不是会同步修改，前面已经执行了吧空的给
                        byServerReceiveMsg.setSocket(socket);     //监听器换监听的socket
                        byServerReceiveMsg.setServerUI(jFrame);
                        byServerReceiveMsg.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    //连接客户端，获得客户端socket
    public Socket connect() throws IOException {
        Socket socket_client = serverSocket.accept();
        return socket_client;
    }

    //从数据库获取用户数据
    public void getUsers(){
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("users.csv");
            InputStream inputStream= new FileInputStream("users.csv") ;
            IOMsg_Str ioMsg_str = new IOMsg_Str();
            IOMsg_INT ioMsg_Int = new IOMsg_INT();
            ioMsg_Int.sendInt(outputStream,3);
            ioMsg_str.SendStr(outputStream,"BJT");
            ioMsg_Int.sendInt(outputStream,1015);
            ioMsg_str.SendStr(outputStream,"1111");
            ioMsg_str.SendStr(outputStream,"stefen");
            ioMsg_Int.sendInt(outputStream,410);
            ioMsg_str.SendStr(outputStream,"1111");
            ioMsg_str.SendStr(outputStream,"eazin");
            ioMsg_Int.sendInt(outputStream,420);
            ioMsg_str.SendStr(outputStream,"1111");

            int userSize = ioMsg_Int.receiveInt(inputStream);
            for (int i=0;i<userSize;i++) {
                String names = ioMsg_str.receiveStr(inputStream);
                int id = ioMsg_Int.receiveInt(inputStream);
                String password = ioMsg_str.receiveStr(inputStream);
                userList.add(new ByUser(names,id,password));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
