package net.by0116;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * color:[0] red [1] green [2] blue
 */

public class IOMsg_Color {

    public void sendColor(OutputStream outputStream,Color color) throws IOException {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        outputStream.write(red);
        outputStream.write(green);
        outputStream.write(blue);

    }
    public Color receiveColor(InputStream inputStream) throws IOException {
        int red = inputStream.read();
        int green = inputStream.read();
        int blue = inputStream.read();
        Color color = new Color(red,green,blue);
        return color;
    }
}
