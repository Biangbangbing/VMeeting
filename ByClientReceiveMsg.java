package net.by0119;

import net.by0118.IOMsg_INT;
import net.by0118.IOMsg_Str;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ByClientReceiveMsg {
    JTextArea dialogTrack;

    public void setDialogTrack(JTextArea dialogTrack) {
        this.dialogTrack = dialogTrack;
    }

    public void receiveMsg(JFrame jFrame, Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        IOMsg_INT ioMsg_int = new IOMsg_INT();
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        int sendID = ioMsg_int.receiveInt(inputStream);
        String sendName = ioMsg_str.receiveStr(inputStream);
        int messageType = inputStream.read();
        String time = ioMsg_str.receiveStr(inputStream);

        if(messageType==1) {
            String msg = ioMsg_str.receiveStr(inputStream);
            dialogTrack.append("\n" + time + "\n" + sendName + " : " + msg);
        }
        else if(messageType == 2){
            int X1 = ioMsg_int.receiveInt(inputStream);
            int Y1 = ioMsg_int.receiveInt(inputStream);
            int X2 = ioMsg_int.receiveInt(inputStream);
            int Y2 = ioMsg_int.receiveInt(inputStream);
            Graphics pen = jFrame.getGraphics();
            pen.drawLine(X1,Y1,X2,Y2);
            dialogTrack.append("\n" + time + "\n" + sendName + " : 发送了涂鸦消息。" );
        }


    }


}
