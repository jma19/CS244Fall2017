package com.uci.utils;

import com.google.common.collect.Lists;
import com.uci.mode.DataType;
import com.uci.mode.ExperimentData;
import com.uci.mode.SamplePair;
import org.junit.Before;
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

    @Test
    public void shouldCreateAndWriteAll() throws Exception {
        FileHandler.createFileAndWrite("data2", Lists.newArrayList(new SamplePair().setIr("xxx").setRed("red"), new
                SamplePair().setRed("xxx").setIr("red")));

    }

    class Node {
        Node ele;
        int val;
    }

    @Test
    public void name() throws Exception {

    }
}
