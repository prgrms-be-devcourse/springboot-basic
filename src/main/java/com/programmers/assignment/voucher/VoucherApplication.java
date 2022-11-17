package com.programmers.assignment.voucher;

import com.programmers.assignment.voucher.engine.controller.MenuController;
import com.programmers.assignment.voucher.engine.io.ConsoleInput;
import com.programmers.assignment.voucher.engine.io.ConsoleOutput;
import com.programmers.assignment.voucher.engine.io.Input;
import com.programmers.assignment.voucher.engine.io.Output;
import com.programmers.assignment.voucher.engine.repository.MemoryVoucherRepository;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.util.domain.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);


    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(VoucherApplication.class, args);

        logger.info("application start");
        applicationContext.getBean(CliApplication.class).run();
        logger.info("application end");
    }

}
