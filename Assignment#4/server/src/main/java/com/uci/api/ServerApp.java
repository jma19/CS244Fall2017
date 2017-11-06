package com.uci.api;

import com.google.common.base.Strings;
import com.uci.mode.AccelerometerData;
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
    private static final String PAIR_SPLITTER = "#";
    private static final String VALUE_SPLITTER = ":";
    private List<SamplePair> samplePairList = new ArrayList<>();
    private static final int MAX_BATCH = 10;
    private static volatile boolean STOP = false;

    private static volatile int currentBachNumber = 0;

    /**
     * @param data
     * @return
     */
    @RequestMapping(path = "/experimentData", method = RequestMethod.POST)
    public Response post(@RequestParam String data) {
        if (!STOP) {
            LOGGER.info(String.format("receive data --->%s", data));
            String[] pairs = data.split(PAIR_SPLITTER);
            for (String pair : pairs) {
                if (!Strings.isNullOrEmpty(pair)) {
                    if (!Strings.isNullOrEmpty(pair)) {
                        String[] split = pair.split(VALUE_SPLITTER);
//                        samplePairList.add(new SamplePair().setRed(split[0]).setIr(split[1]));
                        accelerometerDatas.add(new AccelerometerData(split[0], split[1], split[2]));
                    }
                }
            }
            currentBachNumber++;
            if (currentBachNumber >= MAX_BATCH) {
                LOGGER.info("finishing collecting data..");
                FileHandler.createFileAndWriteAcc("team9_assignment4_test.csv", accelerometerDatas);
                STOP = true;
            }
        }
        return Response.success("OK");
    }

    private List<AccelerometerData> accelerometerDatas = new ArrayList<>();



}
