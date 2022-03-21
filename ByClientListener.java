package net.by0119;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ByClientListener implements MouseListener, ActionListener, ListSelectionListener{
    JFrame clientJFrame;
    Socket socket;
    Graphics pen;
    JTextArea inputArea ;

//    JTextArea showDia;
    JTextPane showDia;

    JTextArea targetID;
    JLabel dialogTitle;
    ByUser localUser ;
    ByUser targetUser ;
    ByClient byClient;
    ByClientUI byClientUI;
    ByClientReceiveMsg byClientReceiveMsg;

    public void setByClientUI(ByClientUI byClientUI) {
        this.byClientUI = byClientUI;
    }

    int messageType = 1;   //1.文字str   2.直线    3.图片    4.
    int X1, Y1, X2, Y2;
    BufferedImage sendImage ;

    public void setByClientReceiveMsg(ByClientReceiveMsg byClientReceiveMsg) {
        this.byClientReceiveMsg = byClientReceiveMsg;
    }

    IOMsg_INT ioMsg_int = new IOMsg_INT();
    IOMsg_Str ioMsg_str = new IOMsg_Str();
    IOMsg_Image ioMsg_image = new IOMsg_Image();

    public void setTargetID(JTextArea targetID) {
        this.targetID = targetID;
    }

    public void setByClient(ByClient byClient) {
        this.byClient = byClient;
    }

    public ByClientListener(JFrame clientJFrame, Socket socket,JTextArea inputArea,JTextPane showDia,JLabel dialogTitle,ByUser localUser,ByUser targetUser) {
        this.clientJFrame = clientJFrame;
        this.socket = socket;
        this.inputArea = inputArea;
        this.showDia = showDia;
        this.dialogTitle = dialogTitle;

        this.localUser = localUser;
        this.targetUser = targetUser;

        this.pen = clientJFrame.getGraphics();
//        if(this.pen==null)
//            System.out.println("pen is null");
//        else
//            System.out.println("not null");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String BtnStr = e.getActionCommand();
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.targetUser.setID(Integer.valueOf(this.targetID.getText()).intValue());


        if(BtnStr.equals("发送")){
            try {
                System.out.println("点击发送信息");

                String msg = this.inputArea.getText();
                if (msg != null)
                    messageType = 1;
                System.out.println(msg);

                MessagePackage sendMessagePackage = new MessagePackage(this.localUser.getID(),this.targetUser.getID(),messageType);
                //发送消息包：自己的ID,name，消息类型，日期，消息内容

                if(messageType == 1) {
                    sendMessagePackage.sendMessHeader2(outputStream);
                    ioMsg_str.SendStr(outputStream,msg);
//                    showDia.append("\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getID()+" : "+msg);
                    showDia.setText(showDia.getText() + "\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getID()+" : "+msg);
                    inputArea.setText("");
                }
                // 选择点击发送还是选择好图片就发送
//                if(messageType == 10) {
//                    sendMessagePackage.sendMessHeader2(outputStream);
//                    ioMsg_str.SendStr(outputStream,msg);
//                    showDia.append("\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getID()+" : "+msg);
//                    inputArea.setText("");
//
//                }
                outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if(BtnStr.equals("涂鸦")){
            if(messageType!=2)
                messageType = 2;
            else
                messageType = 1;
        }
        else if(BtnStr.equals("图片")){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new ImageFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);

            int option = fileChooser.showOpenDialog(clientJFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(file);
                    sendImage = ImageIO.read(inputStream);
                    System.out.println("成功获得图片");
                    messageType = 10;
                    MessagePackage sendMessagePackage = new MessagePackage(this.localUser.getID(),this.targetUser.getID(),messageType);
                    sendMessagePackage.sendMessHeader2(outputStream);
                    ioMsg_image.sendImage(outputStream,sendImage);
//                    showDia.append("\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getID()+" : ");
                    showDia.setText(showDia.getText() + "\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getID()+" : ");
                    ImageIcon icon = new ImageIcon(sendImage);
                    showDia.insertIcon(icon);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
        else if(BtnStr.equals("视频通话")){
            messageType = 12;
            JFrame jFrame = byClientUI.videoUI(this.socket,this.byClientReceiveMsg);


        }
        else if(BtnStr.equals("文件")){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new ImageFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);

            int option = fileChooser.showOpenDialog(clientJFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                InputStream inputStream = null;
//                try {

                    System.out.println("成功获得文件");
                    messageType = 13;
                    MessagePackage sendMessagePackage = new MessagePackage(this.localUser.getID(),this.targetUser.getID(),messageType);
                    sendMessagePackage.sendMessHeader2(outputStream);

//                    showDia.append("\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getID()+" : ");
                    showDia.setText(showDia.getText() + "\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getID()+" : ");


//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            }

        }
        else if(BtnStr.equals("清空")){ //清空输入区
            inputArea.setText("");
        }
        else if(BtnStr.equals("清空聊天记录")){ //清空显示区
            showDia.setText("");
            clientJFrame.repaint();
        }
        else if("log out".equals(BtnStr)){
            try {
                this.localUser.setState(0);
                System.out.println("向服务器发送离线请求。");
                MessagePackage sendMessagePackage = new MessagePackage(this.localUser.getID(),this.targetUser.getID(),253);

//                OutputStream outputStream = socket.getOutputStream();
                sendMessagePackage.sendMessHeader2(outputStream);

                clientJFrame.setVisible(false);
                new ByClientLoginUI().login(this.byClient);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
        System.out.println(X1);
        System.out.println(Y1);

        if (messageType == 2) {
            this.pen.drawLine(X1, Y1, X2, Y2);
            OutputStream outputStream = null;
            try {
                outputStream = socket.getOutputStream();
                MessagePackage sendMessagePackage = new MessagePackage(this.localUser.getID(),this.localUser.getName(),this.targetUser.getID(),messageType);
                sendMessagePackage.sendMessHeader2(outputStream);
                //发送消息包：自己的ID,name，消息类型，日期，消息内容
                IOMsg_INT ioMsg_int = new IOMsg_INT();
                IOMsg_Str ioMsg_str = new IOMsg_Str();

                ioMsg_int.sendInt(outputStream,X1);
                ioMsg_int.sendInt(outputStream,Y1);
                ioMsg_int.sendInt(outputStream,X2);
                ioMsg_int.sendInt(outputStream,Y2);
//                showDia.append("\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getName()+" : 发送了涂鸦消息。");
                showDia.setText(showDia.getText() + "\n"+sendMessagePackage.getDateStr()+"\n"+this.localUser.getName()+" : 发送了涂鸦消息。");
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
