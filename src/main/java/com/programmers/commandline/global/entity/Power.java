package com.programmers.commandline.global.entity;

public class Power {

    private boolean power;

    public boolean isPower() {
        return power;
    }

    public void powerOff() {
        this.power = false;
    }

    public void powerOn() {
        this.power = true;
    }
}
