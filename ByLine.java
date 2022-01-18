package net.by0116;

/**
 * ByLine: 自己创造的2D直线类
 * 属性：
 *     直线两点的坐标：X1，Y1，X2，Y2
 *
 */
public class ByLine {
    int X1,Y1,X2,Y2;

    public ByLine(int x1, int y1, int x2, int y2) {
        X1 = x1;
        Y1 = y1;
        X2 = x2;
        Y2 = y2;
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
}
