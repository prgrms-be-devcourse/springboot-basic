package com.prgrms.springbootbasic.app;

import org.springframework.stereotype.Component;

@Component
public class ApplicationStatus {

    private boolean running;

    public ApplicationStatus() {
        running = true;
    }

    public boolean isRunning(){
        return running;
    }

    public void exit(){
        running = false;
    }
}
