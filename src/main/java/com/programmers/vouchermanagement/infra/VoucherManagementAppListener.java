package com.programmers.vouchermanagement.infra;

import com.programmers.vouchermanagement.infra.io.ConsoleOutput;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagementAppListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConsoleOutput.printHelp();
    }
}
