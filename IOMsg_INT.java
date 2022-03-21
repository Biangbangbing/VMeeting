package net.by0119;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * int类型读写
 *
 * 1.send（写）:
 *   输入: int
 *   输出: int数组 4个  0:31——24   1:23——16   2:15——8   3:7——0
 *   操作: 分别将四个字节移位然后保存（保存到int是想只存每一位数字0-255，忽略正负号）
 *
 * 2.receive（读）:
 *   输入:inputStream读出的4个byte数组，转int
 *   输出:一个int型整数
 *   操作：按位排布，依次放到正确的位然后加起来（拼接）
 *
 *
 */
public class IOMsg_INT {

    public void sendInt(OutputStream outputStream,int number) throws IOException {
        int[] buffer = new int[4];
        for(int i=0;i<4;i++) {
            buffer[i] = (byte) (number >> (24 - i * 8)) & 0xff;
            outputStream.write(buffer[i]);
        }
        //return buffer;

//        buffer[0]= number>>24 &0xff;
//        buffer[1]= number>>16 &0xff;
//        buffer[2]= number>>8 &0xff;
//        buffer[3]= number>>0 &0xff;

    }

    public int receiveInt(InputStream inputStream) throws IOException {
        int number = 0;
        for(int i=0;i<4;i++)
            number += inputStream.read()<<(24-i*8);
        System.out.println("读出来的数字："+number);
        return number;
        /*number = 0;
        number += buffer[0]<<24 &0xff;
        number += buffer[1]<<16 &0xff;
        number += buffer[2]<<8 &0xff;
        number += buffer[3]<<0 &0xff;*/

    }
}
