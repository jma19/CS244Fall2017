package com.uci.api;

import com.uci.mode.AccelerometerData;
import com.uci.mode.Data;
import com.uci.mode.Response;
import com.uci.mode.SamplePair;
import com.uci.utils.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junm5 on 10/14/17.
 */
@RestController
public class ServerApp {
    /**
     * Logger constant
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApp.class);
    private static final String TYPE_SPLITTER = "\\$";
    private static final String PAIR_SPLITTER = "#";
    private static final String VALUE_SPLITTER = ":";
    private List<SamplePair> samplePairList = new ArrayList<>();
    private static final int MAX_BATCH = 133;
    private static volatile boolean STOP = false;
    private static volatile int currentBachNumber = 0;
    private int time = 0;
    private int frequency = 20;
    private List<Data> dataOf6 = new ArrayList<>();

    private Long start = null;
    private boolean isFirst = false;
    private long totalTime = 0;
    private long pre = 0;
    long timeDiff = 0;
    private List<String> receivedData = new ArrayList<>();

    /**
     * @param data
     * @return
     */
    @RequestMapping(path = "/experimentData", method = RequestMethod.POST)
    public void post(@RequestParam String data) {
        if (!STOP) {
            LOGGER.info(String.format("receive data --->%s", data));
            receivedData.add(data);
            if (pre == 0) {
                pre = System.currentTimeMillis();
            } else {
                long current = System.currentTimeMillis();
                timeDiff = current - pre;
                pre = current;
                totalTime += timeDiff;
            }
            if (totalTime >= 60 * 1000 * 10) {
                LOGGER.info("finishing collecting data..");
                for (String str : receivedData) {
                    storeIntoDataOf6(str);
                }
                long diff = totalTime;
                double dev = diff * 1.0 / (1000 * dataOf6.size());
                for (Data data1 : dataOf6) {
                    Integer time = Integer.valueOf(data1.getTime());
                    String newTime = String.format("%.2f", time * dev);
                    data1.setTime(newTime);
                }
                FileHandler.createFileAndWriteAcc("team9_assignment6_sleeping.csv", dataOf6);
                STOP = true;
            }
        }
    }

    private void storeIntoDataOf6(@RequestParam String data) {
        String[] typeData = data.split(TYPE_SPLITTER);
        String xyz = typeData[0];
        String irred = typeData[1];

        String[] xyzPairs = xyz.split(PAIR_SPLITTER);
        String[] irPairs = data.split(PAIR_SPLITTER);

        assert (xyzPairs.length == irPairs.length);

        for (int i = 0; i < irPairs.length; i++) {
            String[] xyzPair = xyzPairs[i].split(VALUE_SPLITTER);
            String[] irPair = irPairs[i].split(VALUE_SPLITTER);
            Data reciveData = new Data(String.valueOf(time), irPair[0], irPair[1], xyzPair[0], xyzPair[1], xyzPair[2]);
            time += 1;
            dataOf6.add(reciveData);
        }
    }
//
//    private void storeIntoDataOf6(@RequestParam String data) {
//        //     String[] typeData = data.split(TYPE_SPLITTER);
//        //     String xyz = typeData[0];
//        //     String irred = typeData[1];
//
//        //     String[] xyzPairs = xyz.split(PAIR_SPLITTER);
//        String[] irPairs = data.split(PAIR_SPLITTER);
//
//        //      assert (xyzPairs.length == irPairs.length);
//
//        for (int i = 0; i < irPairs.length; i++) {
//            //         String[] xyzPair = xyzPairs[i].split(VALUE_SPLITTER);
//            String[] irPair = irPairs[i].split(VALUE_SPLITTER);
//            // public Data(String time, String IR, String RED, String x, String y, String z) {
//            //   Data reciveData = new Data(String.valueOf(time), irPair[0], irPair[1], xyzPair[0], xyzPair[1], xyzPair[2]);
//            Data reciveData = new Data(String.valueOf(time), irPair[0], irPair[1], "0", "0", "0");
//
//            time += 1;
//            dataOf6.add(reciveData);
//        }
//    }

    private List<AccelerometerData> accelerometerDatas = new ArrayList<>();


}
