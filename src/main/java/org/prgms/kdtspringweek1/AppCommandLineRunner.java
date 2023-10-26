package org.prgms.kdtspringweek1;

import org.prgms.kdtspringweek1.controller.AppController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppCommandLineRunner implements CommandLineRunner {

    private final AppController appController;

    public AppCommandLineRunner(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void run(String... args) {
        appController.startVoucherProgram();
    }
}
