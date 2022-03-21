package net.by0119;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ByClientStart extends Thread{
    @Override
    public void run() {
        try {
            new ByClient("localhost",8888);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws FileNotFoundException {
        new ByClientStart().start();
        new ByClientStart().start();
        new ByClientStart().start();
    }
//    public static void main(String[] args) throws IOException {
//        new ByClient("localhost",8888,"BJT",1015,"1111");
//    }
}
