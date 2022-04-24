package com.dojinyou.devcourse.voucherapplication.voucher.Entity;

public class PercentAmount {
    int value;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;


    PercentAmount(int value) {
        isValidRange(value);
        this.value = value;
    }

    private void isValidRange(int value) {
        if (value < MIN_VALUE || MAX_VALUE < value) {
            throw new IllegalArgumentException("범위가 안 맞다.");
        }
    }
}