package com.prgrms.view.command;

public enum Power {
    ON,
    OFF;

    public boolean isOn() {
        return this.equals(ON);
    }

}
