package net.by0119;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ByServerListener implements MouseListener, ActionListener, ListSelectionListener {
    JFrame jFrame;
    Socket socket;
    Graphics pen;
    JTextArea inputArea ;
    JTextArea showDia;
    JTextArea targetID;
    JLabel dialogTitle;
    String name;
    int ID;

    int messageType = 1;   //1.文字str   2.直线    3.图片    4.
    int X1, Y1, X2, Y2;

    public ByServerListener(JFrame jFrame, Socket socket, JTextArea inputArea, JTextArea showDia, JLabel dialogTitle, String name, int ID) {
        this.jFrame = jFrame;
        this.socket = socket;
        this.inputArea = inputArea;
        this.showDia = showDia;
        this.dialogTitle = dialogTitle;
        this.name = name;
        this.ID = ID;
        this.pen = jFrame.getGraphics();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String BtnStr = e.getActionCommand();
        if(BtnStr.equals("发送")){
            try {
                    System.out.println("点击发送信息");
                    OutputStream outputStream = socket.getOutputStream();
                    String msg = this.inputArea.getText();
                    System.out.println(msg);


                    Date date = new Date() ;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dataStr = dateFormat.format(date);
                    System.out.println("时间长度"+date.toString());
                    //发送消息包：自己的ID,name，消息类型，日期，消息内容

                    IOMsg_INT ioMsg_int = new IOMsg_INT();
                    IOMsg_Str ioMsg_str = new IOMsg_Str();
                    ioMsg_int.sendInt(outputStream,ID);
                    ioMsg_str.SendStr(outputStream,name);
                    outputStream.write(messageType);
                    ioMsg_str.SendStr(outputStream,dataStr);
                    if(messageType == 1) {
                        ioMsg_str.SendStr(outputStream,msg);
                    }
                    //outputStream.write();
                    //jFrame.get
                    showDia.append("\n"+dataStr+"\n"+name+" : "+msg);
                    inputArea.setText("");
                    outputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

        }
        else if(BtnStr.equals("清空")){ //清空输入区
            inputArea.setText("");
        }
        else if(BtnStr.equals("清空聊天记录")){ //清空显示区
            showDia.setText("");
            jFrame.repaint();
        }
        else if(BtnStr.equals("涂鸦")){
            if(messageType!=2)
                messageType = 2;
            else
                messageType = 1;
//            JFrame paintBoard = new JFrame("涂鸦板——S");
//            paintBoard.setSize();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        X1 = e.getX();
        Y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        X2 = e.getX();
        Y2 = e.getY();
        if (messageType == 2) {
            pen.drawLine(X1, Y1, X2, Y2);
            OutputStream outputStream = null;
            try {
                outputStream = socket.getOutputStream();
                Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = dateFormat.format(date);
                System.out.println("时间长度"+date.toString());
                //发送消息包：自己的ID,name，消息类型，日期，消息内容

                IOMsg_INT ioMsg_int = new IOMsg_INT();
                IOMsg_Str ioMsg_str = new IOMsg_Str();
                ioMsg_int.sendInt(outputStream,ID);
                ioMsg_str.SendStr(outputStream,name);
                outputStream.write(messageType);
                ioMsg_str.SendStr(outputStream,dateStr);

                ioMsg_int.sendInt(outputStream,X1);
                ioMsg_int.sendInt(outputStream,Y1);
                ioMsg_int.sendInt(outputStream,X2);
                ioMsg_int.sendInt(outputStream,Y2);

                showDia.append("\n"+dateStr+"\n"+name+" : 发送了涂鸦消息。");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
