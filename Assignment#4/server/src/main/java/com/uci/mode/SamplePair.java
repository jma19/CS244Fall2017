package com.uci.mode;

/**
 * Created by junm5 on 10/22/17.
 */
public class SamplePair {
    private String red;
    private String ir;

    public String getRed() {
        return red;
    }

    public SamplePair setRed(String red) {
        this.red = red;
        return this;
    }

    public String getIr() {
        return ir;
    }

    public SamplePair setIr(String ir) {
        this.ir = ir;
        return this;
    }

    @Override
    public String toString() {
        return "SamplePair{" +
                "red='" + red + '\'' +
                ", ir='" + ir + '\'' +
                '}';
    }
}
