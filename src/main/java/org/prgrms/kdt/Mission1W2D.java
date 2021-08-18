package org.prgrms.kdt;

import org.prgrms.kdt.config.MissionConfiguration;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 12:53 오전
 */
public class Mission1W2D {

    public static void main(String[] args) {
        VoucherService voucherService = getVoucherService(getApplicationContext());

        new CommandLineApplication(new Console(), voucherService).run();
    }

    private static AnnotationConfigApplicationContext getApplicationContext() {
        return new AnnotationConfigApplicationContext(MissionConfiguration.class);
    }

    private static VoucherService getVoucherService(AnnotationConfigApplicationContext applicationContext) {
        return applicationContext.getBean(VoucherService.class);
    }
}
