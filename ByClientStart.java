package net.by0119;

import java.io.IOException;

public class ByClientStart {
    public static void main(String[] args) throws IOException {
        new ByClient("localhost",8888);
    }
}
