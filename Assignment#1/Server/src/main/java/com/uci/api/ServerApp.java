package com.uci.api;

import com.uci.mode.DataType;
import com.uci.mode.ExperimentData;
import com.uci.mode.Response;
import com.uci.utils.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by junm5 on 10/14/17.
 */
@RestController
public class ServerApp {
    /**
     * Logger constant
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApp.class);

    /**
     * @param data
     * @return
     */
    @RequestMapping(path = "/experimentData", method = RequestMethod.POST)
    public Response post(@RequestParam String data) {
        LOGGER.info(String.format("receive data --->%s", data));
        ExperimentData experimentData = new ExperimentData().setDataType(DataType.WALKING)
                .setTimeStamp(System.currentTimeMillis())
                .setValue(data);
        FileHandler.append(experimentData);
        return Response.success("OK");
    }
}
