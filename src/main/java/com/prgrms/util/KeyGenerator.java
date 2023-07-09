package com.prgrms.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KeyGenerator {

    private static Random rand = new Random();

    public int make() {
        return Math.abs(rand.nextInt());
    }
}
