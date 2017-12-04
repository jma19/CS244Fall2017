package com.uci.api;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by junm5 on 11/6/17.
 */
public class ServerAppTest {
    @Test
    public void name() throws Exception {
        String str = "0.09:0.02:1.01#0.09:0.02:1.00#0.09:0.02:1.01#0.09:0.03:1.01#0.09:0.02:1.01#0.09:0.02:1.01#0.09:0.02:1.00#0.09:0.03:1.01#0.09:0.02:1.00#0.09:0.02:1.01#0.09:0.02:1.01#0.09:0.02:1.00#0.09:0.02:1.00#0.09:0.02:1.01#0.09:0.02:1.01#0.09:0.02:1.01#0.09:0.02:1.01#0.09:0.02:1.01#0.09:0.02:1.01#0.09:0.02:1.01#$625:926#641:840#518:905#618:855#597:918#622:907#687:796#577:943#659:916#629:886#530:994#631:865#577:936#610:896#641:791#558:911#672:879#677:896#523:927#678:860#";
        String[] res = str.split("\\$");
        System.out.println(res.length);
    }
}