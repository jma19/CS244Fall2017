package com.uci.utils;

import com.uci.mode.DataType;
import com.uci.mode.ExperimentData;
import org.junit.Test;

/**
 * Created by junm5 on 10/14/17.
 */
public class FileHandlerTest {




    @Test
    public void shouldAppendData() throws Exception {
        ExperimentData experimentData = new ExperimentData().setDataType(DataType.WALKING).setTimeStamp(System.currentTimeMillis()).setValue("1");
        FileHandler.append(experimentData);

    }
}