package com.prgrms.model;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KeyGenerator {

    private KeyGenerator() {}

    private static Random rand = new Random();

    public int make() {
        return Math.abs(rand.nextInt());
    }

}
