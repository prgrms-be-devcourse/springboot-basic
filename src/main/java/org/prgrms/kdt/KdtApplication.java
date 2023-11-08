package org.prgrms.kdt;

import org.prgrms.kdt.io.ModeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class KdtApplication {

    private final ModeController modeController;

    @Autowired
    public KdtApplication(ModeController modeController) {
        this.modeController = modeController;
    }

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);

        KdtApplication kdtApplication = applicationContext.getBean(KdtApplication.class);
        kdtApplication.run();
    }

    public void run() throws IOException {
        while (true) {
            if (!modeController.modeStart()) {
                break;
            }
        }
    }
}
