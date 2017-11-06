package com.uci.utils;

import com.uci.mode.AccelerometerData;
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

    public static void createFileAndWriteAcc(String fileName, List<AccelerometerData> accelerometerDatas) {
        try {
            PrintWriter pw = new PrintWriter(new File(fileName));
            StringBuilder sb = new StringBuilder();
            sb.append("X").append(',').append("Y").append(",").append("Z").append('\n');
            for (AccelerometerData accelerometerData : accelerometerDatas) {
                sb.append(accelerometerData.getX()).append(",").append(accelerometerData.getY()).append(",").append(accelerometerData.getZ()).append("\n");
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
