package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.prgrms.kdt.voucher.presentation.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.text.SimpleDateFormat;

// MISSION-1,2 :: Mission-3와 조금은 다른 맥락인 것 같아서 분리해서 진행했습니다.
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        logger.info("logger name => " + logger.getName());
        logger.info("start system in => " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        logger.info("is applicationContext -> ", applicationContext);
        applicationContext.register(AppConfiguration.class);
        logger.info("register AppConfiguration.class");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        logger.info("set Environment " + environment);
        applicationContext.refresh();
        logger.info("refresh application.refresh() method");

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        logger.info("new instance voucherService " + voucherService);
        new VoucherController(voucherService).play();
        logger.info("START SYSTEM");

    }
}
