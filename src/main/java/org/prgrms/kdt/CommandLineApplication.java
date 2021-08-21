package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.prgrms.kdt.voucher.presentation.VoucherController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

// MISSION-1,2 :: Mission-3와 조금은 다른 맥락인 것 같아서 분리해서 진행했습니다.
public class CommandLineApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        new VoucherController(voucherService).play();
    }
}
