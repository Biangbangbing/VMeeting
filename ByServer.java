package net.by0116;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ByServer {
    private ServerSocket serverSocket = null;
    Socket getClients[] = new Socket[100];
    public void getConnection () throws IOException, InterruptedException {
        serverSocket = new ServerSocket(50001);
        //接收请求，建立连接
        System.out.println("监听中……");
        for (int i=0;i<100;i++) {
            //建立连接
            System.out.println(i);
            Socket socket_c = serverSocket.accept();
            System.out.println("监听到并连接。" + i);
            getClients[i] = socket_c;
            System.out.println("服务器已连接 客户端:" + i + " 该客户端port为:" + socket_c.getLocalPort());
            Thread.sleep(1000);

            int times =0 ;
            while (true){
                times++;
                if(times==10) {
                    System.out.println("server:聊天次数已达上限，bye");
                    OutputStream os = getClients[i].getOutputStream();
//                    new IOMsg_Str().SendStr(os,"bye");
                    new IOMsg_INT().sendInt(os,-1);
                    os.flush(); //不刷新，不关闭，客户端没有收到读完的消息
                    os.close();
                    break;
                }
                //发消息给客户端
                OutputStream os = getClients[i].getOutputStream();

                //发str：
//                String msg = "Hello! " + i + " I am server.";
//                new IOMsg_Str().SendStr(os,msg);

                //发int
                int msg = 410420;
                new IOMsg_INT().sendInt(os,msg);

                System.out.println("server: "+msg);
                os.flush(); //不刷新，不关闭，客户端没有收到读完的消息
//                os.close();
//                System.out.println(" 服务器 向 客户端 " + i + " 发消息结束。");

                //收到来自客户端的回复
                InputStream is = getClients[i].getInputStream();
                //收str
                String msgBack = new IOMsg_Str().receiveStr(is);
                //收int
//                int msgBack = new IOMsg_INT().receiveInt(is);
                System.out.println("C-bjt: " + msgBack);
            }
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new ByServer().getConnection();
    }
}
