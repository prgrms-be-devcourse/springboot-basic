package org.programmers.kdt.weekly.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.programmers.kdt.weekly.command.io.Console;
import org.programmers.kdt.weekly.command.io.InfoMessageType;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.customer.service.CustomerService;
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
            this.console.printCommandDescription(getCommandDescription());
            var userInput = this.console.getUserInput();

            try {
                commandType = CustomerCommandType.of(userInput);

                switch (commandType) {
                    case CUSTOMER_CREATE -> this.createCustomer();
                    case CUSTOMER_LIST -> this.showCustomerList(CustomerType.NORMAL);
                    case CUSTOMER_BLACK_LIST -> this.showCustomerList(CustomerType.BLACK);
                    case CUSTOMER_TYPE_CHANGE -> this.changeCustomerType();
                    case EXIT -> this.console.programExitMessage();
                }
            } catch (IllegalArgumentException e) {
                logger.error("customerCommandLine error -> {]", e);
                this.console.printInfoMessage(InfoMessageType.INVALID);
            }
        }
    }

    private void createCustomer() {
        this.console.print("input name");
        var userName = console.getUserInput();
        this.console.print("input email");
        var userEmail = console.getUserInput();

        if (isDuplicateEmail(userEmail)) {
            this.console.printInfoMessage(InfoMessageType.DUPLICATE_EMAIL);

            return;
        }

        this.customerService.create(UUID.randomUUID(), userEmail, userName);
        this.console.print("success !");
    }

    private void showCustomerList(CustomerType customerType) {
        var customerList = this.customerService.findByCustomerType(customerType);

        if (customerList.isEmpty()) {
            this.console.printInfoMessage(InfoMessageType.CUSTOMER_EMPTY);

            return;
        }

        customerList.forEach((customer) -> System.out.println(customer.toString()));
    }

    private void changeCustomerType() {
        this.console.print("input customer email");
        var customerEmail = this.console.getUserInput();
        var maybeCustomer = customerService.findByEmail(customerEmail);

        if (maybeCustomer.isEmpty()) {
            this.console.printInfoMessage(InfoMessageType.INVALID);

            return;
        }

        this.console.print("input type");
        var changeType = this.console.getUserInput();
        maybeCustomer.get().changeCustomerType(CustomerType.valueOf(changeType));
        this.customerService.changeBlackType(maybeCustomer.get());
        this.console.print("success !");
    }

    private boolean isDuplicateEmail(String userInput) {
        return this.customerService.findByEmail(userInput).isPresent();
    }

    private List<String> getCommandDescription() {
        List<String> commandDescription = new ArrayList<>();
        Arrays.stream(CustomerCommandType.values())
            .forEach((v) -> commandDescription.add(v.getCommandMessage()));

        return commandDescription;
    }
}