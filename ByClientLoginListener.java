package net.by0119;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ByClientLoginListener implements ActionListener, MouseListener {
    JFrame loginUI;
    ByClientLoginUI byClientLoginUI;
    //JFrame registerUI;
    Graphics pen ;
    ByClientReceiveMsg byClientReceiveMsg ;

    JTextField nameField ;
    JTextField idField ;
    JTextField passwordField ;
    ArrayList<ByUser> userList = new ArrayList<>();

    ByClient byClient;

    public ByClientLoginListener(ByClientLoginUI byClientLoginUI,JFrame loginUI,Graphics pen, ByClient byClient,ByClientReceiveMsg byClientReceiveMsg,JTextField idField, JTextField passwordField) throws FileNotFoundException {
        this.byClientLoginUI = byClientLoginUI;
        this.loginUI = loginUI;
        this.pen = pen;
        this.byClient = byClient;
        this.byClientReceiveMsg = byClientReceiveMsg;
        //this.nameField = nameField;
        this.idField = idField;
        this.passwordField = passwordField;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //File users = new File("users.csv");
//        String getName = nameField.getText();

        String str = e.getActionCommand();
        if("登录".equals(str)){
            int getID = Integer.valueOf(idField.getText()).intValue();
            String getPassword = passwordField.getText();
            System.out.println(" "+getID+" "+getPassword);
            System.out.println(userList.size());
            try {
                OutputStream outputStream = byClient.getSocket().getOutputStream();
                MessagePackage sendMessagePackage = new MessagePackage(getID,0,255);
                sendMessagePackage.sendMessHeader2(outputStream);
                //发送消息包：自己的ID,name，消息类型，日期，消息内容

                IOMsg_INT ioMsg_int = new IOMsg_INT();
                IOMsg_Str ioMsg_str = new IOMsg_Str();

                //InputStream inputStream = byClient.getSocket().getInputStream();
                //ioMsg_str.SendStr(outputStream,getName);
                ioMsg_int.sendInt(outputStream,getID);
                ioMsg_str.SendStr(outputStream,getPassword);
                byClientReceiveMsg.setLocalUser(new ByUser(getID,getPassword));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            for(int i=0;i<userList.size();i++){
//                if(userList.get(i).getID() == getID && userList.get(i).getPassword().equals(getPassword)) {
//                   System.out.println(getName+" ，登录成功，欢迎回来！");
//                   this.loginUI.setVisible(false);
//
//                   byClient.setName(getName);
//                   byClient.setID(getID);
//                   byClient.setPassword(getPassword);
//
//                   JFrame clientUI = new ByClientUI().initUI("Client-0",byClient.getSocket(),getName,getID,byClient.getByClientReceiveMsg());
//                   byClient.setClientUI(clientUI);
//
//                }
//            }

        }
        else if ("注册".equals(str)){
            System.out.println("用户正在注册。");
            loginUI.setVisible(false);
            this.byClientLoginUI.register(this.byClient);

//            try {
//
//                OutputStream outputStream = new FileOutputStream("users.csv");
//                IOMsg_Str ioMsg_str = new IOMsg_Str();
//                IOMsg_INT ioMsg_Int = new IOMsg_INT();
//                ioMsg_str.SendStr(outputStream,getName);
//                ioMsg_Int.sendInt(outputStream,getID);
//                ioMsg_str.SendStr(outputStream,getPassword);
//                userList.add(new ByUser(getName,getID,getPassword));
//                System.out.println("注册成功。");
//            } catch (FileNotFoundException ex) {
//                ex.printStackTrace();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
