package org.prgrms.vouchermanager;

import org.prgrms.vouchermanager.configuration.AppConfiguration;
import org.prgrms.vouchermanager.shell.VoucherManagerShell;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherManagerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherManagerShell voucherManagerShell = applicationContext.getBean(VoucherManagerShell.class);
        voucherManagerShell.run();
    }

}
