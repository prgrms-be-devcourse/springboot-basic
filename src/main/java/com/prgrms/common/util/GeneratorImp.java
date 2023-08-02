package com.prgrms.common.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KeyGeneratorImp implements KeyGenerator{

    private static final Random rand = new Random();

    @Override
    public int make() {
        return Math.abs(rand.nextInt());
    }

}
