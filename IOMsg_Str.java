package net.by0116;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * str 类型读写
 *
 * 1.send（写）
 *   输入：str
 *   输出：byte数组
 *   操作：存储str长度，str byte类型数组
 *
 * 2.receive（读）
 *   输入：读取inputStream中的byte数组
 *   输出：str
 */

public class IOMsg_Str {
    public void SendStr(OutputStream outputStream,String str) throws IOException {
        byte[] ByteList_Str = str.getBytes();
        outputStream.write(ByteList_Str.length);
        outputStream.write(ByteList_Str);
    }
    public String receiveStr(InputStream inputStream) throws IOException {
        int length = inputStream.read();
        System.out.println("端读取信息的长度为：" + length);
        byte[] ByteList_Str= new byte[length];
        inputStream.read(ByteList_Str);
        String result = new String(ByteList_Str);
        return result;
    }
}
