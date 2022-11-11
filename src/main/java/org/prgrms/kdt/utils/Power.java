package org.prgrms.kdt.utils;

public class Power {
    private boolean isRunning;

    public Power() {
        this.isRunning = true;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
    }
}
