package com.prgrms.springbootbasic.app;

class ApplicationStatus {

    private boolean running;

    public ApplicationStatus() {
        running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void exit() {
        running = false;
    }
}
