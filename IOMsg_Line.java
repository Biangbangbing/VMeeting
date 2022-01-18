package net.by0116;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ByLine 类型读写  —— 2D直线
 * 1.send(写)
 *   输入：ByLine对象，4个坐标（X1，Y1，X2，Y2）
 *   输出：
 *   操作：将4个坐标取出分别按int类型存储
 *
 * 2.receive(读)
 *   输入：inputStream 读出的4个int类型的数组
 *   输出：ByLine对象
 *   操作：将坐标按照int类型读出，然后构造ByLine对象，并返回
 */
public class IOMsg_Line {
    public void sendLine(OutputStream outputStream, ByLine line) throws IOException {
        new IOMsg_INT().sendInt(outputStream, line.getX1());
        new IOMsg_INT().sendInt(outputStream, line.getY1());
        new IOMsg_INT().sendInt(outputStream, line.getX2());
        new IOMsg_INT().sendInt(outputStream, line.getY2());
    }
    public ByLine receive(InputStream inputStream) throws IOException {
        int X1 = new IOMsg_INT().receiveInt(inputStream);
        int Y1 = new IOMsg_INT().receiveInt(inputStream);
        int X2 = new IOMsg_INT().receiveInt(inputStream);
        int Y2 = new IOMsg_INT().receiveInt(inputStream);
        ByLine byLine = new ByLine(X1,Y1,X2,Y2);
        return byLine;
    }
}
