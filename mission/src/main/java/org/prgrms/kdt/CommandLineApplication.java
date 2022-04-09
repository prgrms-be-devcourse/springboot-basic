package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;



@Component
public class CommandLineApplication implements ApplicationRunner {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CommandLineApplication(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        output.printMenu();
        while (true){
            input.typeMenu();
        }
    }
}
