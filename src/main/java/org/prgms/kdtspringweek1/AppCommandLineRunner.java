package org.prgms.kdtspringweek1;

import org.prgms.kdtspringweek1.controller.AppController;
import org.prgms.kdtspringweek1.exception.FileExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class AppCommandLineRunner implements CommandLineRunner {

    private final AppController appController;
    private boolean shouldExit = false;
    private final static Logger logger = LoggerFactory.getLogger(AppCommandLineRunner.class);

    public AppCommandLineRunner(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void run(String... args) {
        while (!shouldExit) {
            try {
                appController.startVoucherProgram();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                logger.error(exception.getMessage());
                // 파일에서 정보를 읽어오는 것을 실패했다면, 서버가 켜지지 않는게 정상이므로, 예외 전파 후 어플을 종료 시키도록 한다.
                if (exception.getMessage().equals(FileExceptionCode.FAIL_TO_READ_DATA_FROM_CSV)) {
                    shouldExit = true;
                }
            }
        }
    }

    public void setShouldExit(boolean shouldExit) {
        this.shouldExit = shouldExit;
    }
}
