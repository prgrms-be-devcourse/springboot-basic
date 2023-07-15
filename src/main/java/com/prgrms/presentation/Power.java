package com.prgrms.presentation;

public enum Power {
    ON,
    OFF;

    public boolean isOn() {
        return this.equals(ON);
    }

}
