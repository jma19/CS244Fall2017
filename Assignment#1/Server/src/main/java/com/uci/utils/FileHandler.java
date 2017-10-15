package com.uci.utils;

import com.uci.mode.ExperimentData;

import java.io.*;
import java.util.List;

/**
 * Created by junm5 on 10/14/17.
 */
public class FileHandler {

    private final static String FILENAME = FileHandler.class.getClassLoader().getResource("expertData.txt").getPath();

    public static void append(ExperimentData experimentData) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FILENAME, true), "utf-8"))) {
            writer.write(JsonUtils.toJson(experimentData));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ExperimentData> readAll() {
        return null;
    }
}
