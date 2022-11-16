package org.prgrms.kdt.utils;

public class Power {
    private boolean isRunning;
    private boolean isExceptionExit;

    public Power() {
        this.isRunning = true;
        this.isExceptionExit = false;
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
    }

    public void stopByException(){
        isExceptionExit = true;
    }

    public boolean getIsExceptionExit(){
        return isExceptionExit;
    }
}
