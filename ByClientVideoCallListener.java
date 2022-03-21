package net.by0119;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ByClientVideoCallListener implements ActionListener {
    Socket socket;
    Graphics pen;
    ByUser localUser;
    ByUser targetUser;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setLocalUser(ByUser localUser) {
        this.localUser = localUser;
    }

    public void setTargetUser(ByUser targetUser) {
        this.targetUser = targetUser;
    }

    public void setPen(Graphics pen) {
        this.pen = pen;
    }

    Queue<BufferedImage> imageBuffer = new LinkedList<BufferedImage>();
    IOMsg_Image ioMsg_image = new IOMsg_Image();

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnStr = e.getActionCommand();
        OutputStream outputStream = null;
        if ("拨打".equals(btnStr)) {
            Webcam webcam = Webcam.getDefault ();
            webcam.setViewSize (WebcamResolution.QVGA.getSize ());
            webcam.open ();
            try {
                outputStream = socket.getOutputStream();
                MessagePackage messagePackage = new MessagePackage(this.localUser.getID(),this.targetUser.getID(),12);
                messagePackage.sendMessHeader2(outputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new Thread (new Runnable (){
                @Override
                public void run(){
                    while(true){
                        try {
                            Thread.sleep (30);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace ();
                        }
                        imageBuffer.offer(webcam.getImage ());
                    }
                }
            }).start ();
            OutputStream finalOutputStream = outputStream;
            new Thread (new Runnable (){
                @Override
                public void run(){
                    while(true){
                        try {
                            Thread.sleep (30);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace ();
                        }
                        while (!imageBuffer.isEmpty()) {
                            BufferedImage img = imageBuffer.poll();
                            pen.drawImage(img,50,50,null);
                            try {
                                ioMsg_image.sendImage(finalOutputStream,img);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                        System.out.println("loading……");
                    }
                }
            }).start ();
        }
        else if ("接听".equals(btnStr)) {
            Queue<BufferedImage> imageBuffer = new LinkedList<BufferedImage>();
            InputStream inputStream = null;
            try {
                inputStream = this.socket.getInputStream();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new Thread (new Runnable (){
                @Override
                public void run(){
                    while(!imageBuffer.isEmpty()){
                        try {
                            Thread.sleep (30);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace ();
                        }
                        BufferedImage img = imageBuffer.poll();
                        pen.drawImage (img,50,50,null);
                    }

                }
            }).start ();
            InputStream finalInputStream = inputStream;
            new Thread (new Runnable (){
                @Override
                public void run(){
                    while(true){
                        try {
                            Thread.sleep (30);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace ();
                        }
                        try {
                            imageBuffer.offer(ioMsg_image.receiveImage(finalInputStream));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start ();
        }
        else if ("结束视频".equals(btnStr)) {
            int res = JOptionPane.showConfirmDialog(null,"确认结束视频？","结束视频",JOptionPane.YES_NO_OPTION);
            if (res == 0) {
                System.out.println("结束视频");

            }

        }
    }
}
