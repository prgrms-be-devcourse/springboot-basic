package org.prgrms.kdt.kdtspringorder;

import org.prgrms.kdt.kdtspringorder.common.config.AppConfiguration;
import org.prgrms.kdt.kdtspringorder.voucher.application.VoucherCommandLine;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Voucher Command Line 어플리케이션을 실행한다.
 */
public class VoucherCommandLineApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(VoucherCommandLine.class).start();
        applicationContext.close();

    }

}
