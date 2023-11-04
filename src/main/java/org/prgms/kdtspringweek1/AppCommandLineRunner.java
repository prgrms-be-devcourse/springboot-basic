package org.prgms.kdtspringweek1;

import org.prgms.kdtspringweek1.controller.consoleController.AppController;
import org.prgms.kdtspringweek1.exception.ExitException;
import org.prgms.kdtspringweek1.exception.FileExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class AppCommandLineRunner implements CommandLineRunner {

    private boolean shouldExit = false;
    private final AppController appController;
    private static final Logger logger = LoggerFactory.getLogger(AppCommandLineRunner.class);

    public AppCommandLineRunner(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void run(String... args) {
        while (!shouldExit) {
            try {
                appController.startVoucherProgram();
            } catch (ExitException exception) {
                // 시스템이 종료되어야하는 예외를 구분하여 처리
                System.out.println(exception.getMessage());
                logger.error(exception.getMessage());
                shouldExit = true;
            }
        }
    }

    public void setShouldExit(boolean shouldExit) {
        this.shouldExit = shouldExit;
    }
}
