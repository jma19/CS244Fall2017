package com.uci.utils;

import com.uci.mode.AccelerometerData;
import com.uci.mode.Data;
import com.uci.mode.ExperimentData;
import com.uci.mode.SamplePair;

import java.io.*;
import java.util.List;

/**
 * Created by junm5 on 10/14/17.
 */
public class FileHandler {

    public static void append(ExperimentData experimentData) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("", true), "utf-8"))) {
            writer.write(JsonUtils.toJson(experimentData));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFileAndWrite(String fileName, List<SamplePair> samplePairs) {
        try {
            PrintWriter pw = new PrintWriter(new File(fileName));
            StringBuilder sb = new StringBuilder();
            sb.append("RED1").append(',').append("IR1").append('\n');
            for (SamplePair pair : samplePairs) {
                sb.append(pair.getRed()).append(",").append(pair.getIr()).append("\n");
            }
            pw.write(sb.toString());
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void createFileAndWriteAcc(String fileName, List<Data> accelerometerDatas) {
        try {
            PrintWriter pw = new PrintWriter(new File(fileName));
            StringBuilder sb = new StringBuilder();
            sb.append("time,").append("IR,").append("RED,").append("X,").append("Y,").append("Z\n");
            for (Data data : accelerometerDatas) {
                sb.append(data.getTime() + ",")
                        .append(data.getIR() + ",")
                        .append(data.getRED() + ",")
                        .append(data.getX() + ",")
                        .append(data.getY() + ",")
                        .append(data.getZ() + "\n");
            }
            pw.write(sb.toString());
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static List<ExperimentData> readAll() {
        return null;
    }
}
