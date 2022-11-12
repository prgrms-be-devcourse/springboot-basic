package com.programmers.commandline.global.entity;


import com.programmers.commandline.global.factory.LoggerFactory;

public class Power {

    private boolean power;

    public boolean isPower() {
        LoggerFactory.getLogger().info("Power isPower 실행");
        return power;
    }

    public void powerOff() {
        LoggerFactory.getLogger().info("Power powerOff 실행");
        this.power = false;
    }

    public void powerOn() {
        LoggerFactory.getLogger().info("Power powerOn 실행");
        this.power = true;
    }
}
