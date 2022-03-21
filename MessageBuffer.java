package net.by0119;

import java.awt.image.BufferedImage;

public class MessageBuffer {
    MessagePackage messagePackage ;   // 改成缓存队列：保存多条记录
    String msg;
    BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    int X1;
    int Y1;
    int X2;
    int Y2;

    public MessageBuffer(MessagePackage messagePackage) {
        this.messagePackage = messagePackage;
    }

    public MessagePackage getMessagePackage() {
        return messagePackage;
    }

    public String getMsg() {
        return msg;
    }

    public int getX1() {
        return X1;
    }

    public int getY1() {
        return Y1;
    }

    public int getX2() {
        return X2;
    }

    public int getY2() {
        return Y2;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setX1(int x1) {
        X1 = x1;
    }

    public void setY1(int y1) {
        Y1 = y1;
    }

    public void setX2(int x2) {
        X2 = x2;
    }

    public void setY2(int y2) {
        Y2 = y2;
    }
}
