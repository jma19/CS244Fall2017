package com.uci.mode;

/**
 * Created by junm5 on 11/5/17.
 */
public class AccelerometerData {
    private String x;
    private String y;
    private String z;


    public AccelerometerData(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "AccelerometerData{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
