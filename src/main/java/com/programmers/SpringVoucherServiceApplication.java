package com.programmers;

import com.programmers.voucher.CommandLineApplication;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringVoucherServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringVoucherServiceApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(SpringVoucherServiceApplication.class, args);
        VoucherService voucherService = ac.getBean(VoucherService.class);
        View view = ac.getBean(View.class);

        logger.info("애플리케이션 기동");

        new CommandLineApplication(view, voucherService).run();
    }

}
