package net.by0116;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ByClient {
    private Socket socket_client = null;
    public void sendRequest (int number) throws IOException {
        System.out.println("客户端发送请求");
        socket_client = new Socket("localhost",50001);


        while(true) {
            InputStream is = socket_client.getInputStream();
            //收str
//            String msg = new IOMsg_Str().receiveStr(is);

            //收int
            int msg = new IOMsg_INT().receiveInt(is);
            System.out.println("server: " + msg);
//            System.out.println("客户端接收消息结束。");
//            if(msg.equals("bye"))
            if(msg==-1)
                break;

            //客户端回复服务器
            OutputStream os = socket_client.getOutputStream();
            String msgBack = "bjt-" + number + " ,Hi。";
            new IOMsg_Str().SendStr(os, msgBack);
            System.out.println("bjt: "+msgBack);
            os.flush();
            //os.close();
            //System.out.println("客户端回复结束。");
        }

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new ByClient().sendRequest(0);
    }
}
