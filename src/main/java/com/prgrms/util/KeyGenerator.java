package com.prgrms.util;

import java.util.Random;

public class KeyGenerator {
    private static Random rand = new Random();

    public static int make() {
        return Math.abs(rand.nextInt());
    }
}
