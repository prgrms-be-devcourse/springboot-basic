package com.programmers.commandLine.global.entity;

import org.springframework.stereotype.Component;
/**
 *
 *  Power의 설명을 여기에 작성한다.
 *  어플리케이션의 전원을 담당하는 클래스를 따로 분리 하였습니다.
 *
 *  @author 장주영
 *  @version 1.0.0
 *  작성일 2022/11/03
 *
**/
@Component
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
