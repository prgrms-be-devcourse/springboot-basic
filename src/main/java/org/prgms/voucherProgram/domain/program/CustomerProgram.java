package org.prgms.voucherProgram.domain.program;

import org.prgms.voucherProgram.domain.menu.CustomerMenuType;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.exception.WrongEmailException;
import org.prgms.voucherProgram.exception.WrongNameException;
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
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            CustomerMenuType customerMenuType = inputMenu();
            switch (customerMenuType) {
                case EXIT -> isNotEndProgram = false;
                case CREATE -> createCustomer();
            }
        }
    }

    private void createCustomer() {
        CustomerDto customerDto = inputView.inputCustomerInformation();
        while (true) {
            try {
                outputView.printCustomer(customerService.save(customerDto));
                return;
            } catch (WrongNameException e) {
                outputView.printError(e.getMessage());
                customerDto.setName(inputView.inputCustomerName());
            } catch (WrongEmailException e) {
                outputView.printError(e.getMessage());
                customerDto.setEmail(inputView.inputCustomerEmail());
            }
        }
    }

    private CustomerMenuType inputMenu() {
        while (true) {
            try {
                return CustomerMenuType.from(inputView.inputCustomerMenu());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
