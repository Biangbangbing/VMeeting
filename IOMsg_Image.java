package net.by0119;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Image类型读写
 *
 * 1.sendImage
 *
 * 2.receiveImage
 *
 */
public class IOMsg_Image {
    IOMsg_INT ioMsg_int = new IOMsg_INT();
    public void sendImage (OutputStream outputStream, BufferedImage image) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        ioMsg_int.sendInt(outputStream,height);
        ioMsg_int.sendInt(outputStream,width);
        for (int  i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ioMsg_int.sendInt(outputStream,image.getRGB(i,j));
            }
        }
    }

    public BufferedImage receiveImage (InputStream inputStream) throws IOException {
        int height = ioMsg_int.receiveInt(inputStream);
        int width = ioMsg_int.receiveInt(inputStream);
        int [][]img = new int[height][width];
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i,j,ioMsg_int.receiveInt(inputStream));
//                img[i][j] = ioMsg_int.receiveInt(inputStream);

            }
        }
        return image;
    }

}
