package org.prgrms.vouchermanager;

import org.prgrms.vouchermanager.shell.VoucherManagerShell;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherManagerApplication.class, args);
        applicationContext.getBean(VoucherManagerShell.class).run();
    }

}
