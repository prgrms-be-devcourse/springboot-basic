package org.prgrms.kdt;

import org.prgrms.kdt.io.ModeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class KdtApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);
        ModeController modeController = applicationContext.getBean(ModeController.class);

        boolean isRepeat = true;

        while (isRepeat) {
            isRepeat = modeController.modeList();
        }
    }
}
