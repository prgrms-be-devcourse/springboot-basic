package com.programmers.springbasic.domain.customer.controller;

import com.programmers.springbasic.domain.customer.service.CustomerService;
import com.programmers.springbasic.domain.customer.validator.CustomerCommandValidator;
import com.programmers.springbasic.domain.customer.validator.CustomerCreateRequestValidator;
import com.programmers.springbasic.domain.customer.validator.CustomerIdValidator;
import com.programmers.springbasic.domain.customer.view.CustomerCommandMessage;
import com.programmers.springbasic.domain.customer.view.CustomerCommandOption;
import com.programmers.springbasic.domain.io.IOConsole;
import com.programmers.springbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final VoucherService voucherService;
    private final IOConsole ioConsole;

    public void run() {
        ioConsole.showCustomerMenu();

        String inputCommand = ioConsole.getInput();
        CustomerCommandValidator customerCommandValidator = new CustomerCommandValidator(inputCommand);

        handleCommand(customerCommandValidator);
    }

    private void handleCommand(CustomerCommandValidator customerCommandValidator) {
        String command = customerCommandValidator.getInputCommand();

        if (command.equals(CustomerCommandOption.CREATE.getCommand())) {
            executeCreateCommand();
            return;
        }

        if (command.equals(CustomerCommandOption.LIST.getCommand())) {
            executeListCommand();
            return;
        }

        if (command.equals(CustomerCommandOption.READ.getCommand())) {
            executeReadCommand();
            return;
        }

        if (command.equals(CustomerCommandOption.UPDATE.getCommand())) {    // TODO: Update command
            executeUpdateCommand();
            return;
        }

        if (command.equals(CustomerCommandOption.DELETE.getCommand())) {
            executeDeleteCommand();
            return;
        }

        if (command.equals(CustomerCommandOption.SHOW.getCommand())) {
            executeShowCommand();
            return;
        }

        if (command.equals(CustomerCommandOption.EXIT.getCommand())) {
            executeExitCommand();
        }
    }

    private void executeCreateCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_MESSAGE.getMessage());

        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_NAME_MESSAGE.getMessage());
        String name = ioConsole.getInput();

        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_EMAIL_MESSAGE.getMessage());
        String email = ioConsole.getInput();

        CustomerCreateRequestValidator customerCreateRequestValidator = new CustomerCreateRequestValidator(name, email);

        customerService.createCustomer(customerCreateRequestValidator);
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_CREATE_FINISHED_MESSAGE.getMessage());
    }

    private void executeListCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_LIST_MESSAGE.getMessage());

        ioConsole.printListOutput(customerService.getAllInfo());
    }

    private void executeReadCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_RAED_ID_MESSAGE.getMessage());

        String customerId = ioConsole.getInput();
        CustomerIdValidator customerIdValidator = new CustomerIdValidator(customerId);

        ioConsole.printSingleOutput(customerService.findCustomer(customerIdValidator));
    }

    private void executeUpdateCommand() {   // TODO: Update command
    }

    private void executeDeleteCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_DELETE_ID_MESSAGE.getMessage());

        String customerId = ioConsole.getInput();
        CustomerIdValidator customerIdValidator = new CustomerIdValidator(customerId);

        customerService.removeCustomer(customerIdValidator);
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_DELETE_FINISHED_MESSAGE.getMessage());
    }

    private void executeShowCommand() {
        ioConsole.printSingleOutput("voucher를 조회할 고객의 id을 입력하세요");

        String customerId = ioConsole.getInput();
        CustomerIdValidator customerIdValidator = new CustomerIdValidator(customerId);

        ioConsole.printListOutput(voucherService.getAllVoucherInfoByCustomerId(customerIdValidator));
    }

    private void executeExitCommand() {
        ioConsole.printSingleOutput(CustomerCommandMessage.CUSTOMER_EXIT_MESSAGE.getMessage());
    }
}
