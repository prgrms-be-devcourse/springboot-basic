package org.programmers.kdt.weekly.command;

import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.customer.service.CustomerService;
import org.programmers.kdt.weekly.command.io.Console;
import org.programmers.kdt.weekly.command.io.InputErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandLine {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommandLine.class);

    private final Console console;
    private final CustomerService customerService;

    public CustomerCommandLine(Console console,
        CustomerService customerService) {
        this.console = console;
        this.customerService = customerService;
    }

    public void run() {
        var commandType = CustomerCommandType.DEFAULT;

        while (commandType.isRunnable()) {
            this.console.printCustomerCommand();
            var userInput = this.console.getUserInput();

            try {
                commandType = CustomerCommandType.findByCommand(userInput);
            } catch (IllegalArgumentException e) {
                logger.debug("잘못된 사용자 입력 -> {}", userInput);
            }

            switch (commandType) {
                case CUSTOMER_CREATE -> this.createCustomer();
                case CUSTOMER_LIST -> this.showCustomerList(CustomerType.NORMAL);
                case CUSTOMER_BLACK_LIST -> this.showCustomerList(CustomerType.BLACK);
                case CUSTOMER_TYPE_CHANGE -> this.changeCustomerType();
                case EXIT -> this.console.programExitMessage();
                default -> this.console.printInputErrorMessage(InputErrorType.INVALID);
            }
        }
    }

    private void createCustomer() {
        this.console.printInputMessage("name");
        var userName = console.getUserInput();
        this.console.printInputMessage("email");
        var userEmail = console.getUserInput();

        if (isDuplicateEmail(userEmail)) {
            this.console.printInputErrorMessage(InputErrorType.DUPLICATE_EMAIL);

            return;
        }
        this.customerService.create(userEmail, userName);
        this.console.printExecutionSuccessMessage();
    }

    private void showCustomerList(CustomerType customerType) {
        var customerList = this.customerService.getCustomers(customerType);

        if (customerList.isEmpty()) {
            this.console.printInputErrorMessage(InputErrorType.CUSTOMER_EMPTY);

            return;
        }
        customerList.forEach((customer) -> System.out.println(customer.toString()));
    }

    private void changeCustomerType() {
        this.console.printInputMessage("customer email");
        var customerEmail = this.console.getUserInput();
        var maybeCustomer = customerService.findByEmail(customerEmail);

        if (maybeCustomer.isPresent()) {
            var changeType = this.console.getUserInput();
            maybeCustomer.get().changeCustomerType(CustomerType.valueOf(changeType));
            this.customerService.updateType(maybeCustomer.get());
            this.console.printExecutionSuccessMessage();

            return;
        }
        this.console.printInputErrorMessage(InputErrorType.INVALID);
    }

    private boolean isDuplicateEmail(String userInput) {
        return this.customerService.findByEmail(userInput).isPresent();
    }
}