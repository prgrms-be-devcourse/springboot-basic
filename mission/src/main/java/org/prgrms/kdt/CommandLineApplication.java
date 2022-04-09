package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.VoucherType;
import org.prgrms.kdt.io.exception.InputException;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CommandLineApplication implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
