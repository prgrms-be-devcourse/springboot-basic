package org.prgms.voucherProgram.domain.program;

import org.prgms.voucherProgram.service.CustomerService;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
import org.springframework.stereotype.Component;

@Component
public class CustomerProgram {
    private final CustomerService customerService;
    private final InputView inputView;
    private final OutputView outputView;

    public CustomerProgram(CustomerService customerService, Console console) {
        this.customerService = customerService;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {

    }
}
