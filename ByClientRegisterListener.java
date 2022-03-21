package net.by0119;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;

public class ByClientRegisterListener implements ActionListener, MouseListener {
    JFrame loginUI;
    JFrame registerUI;
    ByClientLoginUI byClientLoginUI ;
    Graphics pen ;
    JTextField nameField ;
    JTextField idField ;
    JTextField passwordField ;
    ByClient byClient;
    ByClientReceiveMsg byClientReceiveMsg;

    public ByClientRegisterListener(ByClientLoginUI byClientLoginUI,JFrame registerUI, Graphics pen, JTextField nameField, JTextField idField, JTextField passwordField,ByClient byClient, ByClientReceiveMsg byClientReceiveMsg) {
        this.registerUI = registerUI;
        this.byClientLoginUI = byClientLoginUI;
        this.pen = pen;
        this.nameField = nameField;
        this.idField = idField;
        this.passwordField = passwordField;
        this.byClient = byClient;
        this.byClientReceiveMsg = byClientReceiveMsg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String getName = nameField.getText();
        int getID = Integer.valueOf(idField.getText()).intValue();
        String getPassword = passwordField.getText();

        String str = e.getActionCommand();
        if("注册".equals(str)){
            OutputStream outputStream = null;
            try {
                outputStream = byClient.getSocket().getOutputStream();
                MessagePackage sendMessagePackage = new MessagePackage(getID,getName,0,254);
                sendMessagePackage.sendMessHeader2(outputStream);
                //发送消息包：自己的ID,name，消息类型，日期，消息内容

                IOMsg_INT ioMsg_int = new IOMsg_INT();
                IOMsg_Str ioMsg_str = new IOMsg_Str();

                ioMsg_str.SendStr(outputStream,getName);
                ioMsg_int.sendInt(outputStream,getID);
                ioMsg_str.SendStr(outputStream,getPassword);
                byClientReceiveMsg.setLocalUser(new ByUser(getName,getID,getPassword));

            } catch (IOException ex) {
                ex.printStackTrace();
            }

//            InputStream inputStream = byClient.getSocket().getInputStream();

            byClientReceiveMsg.setLocalUser(new ByUser(getID,getPassword));
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
