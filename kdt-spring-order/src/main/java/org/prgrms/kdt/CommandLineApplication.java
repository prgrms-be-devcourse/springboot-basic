package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.io.Validator;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Input input = new Input(new BufferedReader(new InputStreamReader(System.in)));
        Output output = new Output();
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        Validator validator = new Validator();

        Console console = new Console(input, output, voucherService, validator);

        while (console.run() != Console.EXIT);
    }
}
