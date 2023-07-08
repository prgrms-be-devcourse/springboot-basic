package com.programmers.springmission;

import com.programmers.springmission.customer.presentation.CustomerController;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.view.Console;
import com.programmers.springmission.view.OptionType;
import com.programmers.springmission.voucher.presentation.VoucherController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ManagementController implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public ManagementController(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    private boolean power = true;

    @Override
    public void run(String... args) {

        while (power) {
            try {
                console.outputDomainOption();
                OptionType inputValue = OptionType.of(console.input());

                switch (inputValue) {
                    case EXIT -> exitVoucherProgram();
                    case VOUCHER -> voucherController.run();
                    case CUSTOMER -> customerController.run();
                }
            } catch (InvalidInputException | IllegalArgumentException | DataAccessException invalidInputException) {
                console.output(invalidInputException.getMessage());
                log.warn(invalidInputException.getMessage());
            }
        }
    }

    private void exitVoucherProgram() {
        console.outputExit();
        power = false;
    }
}

