package com.uci.mode;

import com.google.common.base.Strings;

/**
 * Created by junm5 on 11/16/17.
 */
public class Data {

    private String time;
    private String IR;
    private String RED;
    private String X;
    private String Y;
    private String Z;

    public Data(String time, String IR, String RED, String x, String y, String z) {
        assert (!Strings.isNullOrEmpty(time));
        assert (!Strings.isNullOrEmpty(IR));
        assert (!Strings.isNullOrEmpty(RED));
        assert (!Strings.isNullOrEmpty(X));
        assert (!Strings.isNullOrEmpty(Y));
        assert (!Strings.isNullOrEmpty(Z));


        this.time = time;
        this.IR = IR;
        this.RED = RED;
        X = x;
        Y = y;
        Z = z;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIR() {
        return IR;
    }

    public void setIR(String IR) {
        this.IR = IR;
    }

    public String getRED() {
        return RED;
    }

    public void setRED(String RED) {
        this.RED = RED;
    }

    public String getX() {
        return X;
    }

    public void setX(String x) {
        X = x;
    }

    public String getY() {
        return Y;
    }

    public void setY(String y) {
        Y = y;
    }

    public String getZ() {
        return Z;
    }

    public void setZ(String z) {
        Z = z;
    }
}
