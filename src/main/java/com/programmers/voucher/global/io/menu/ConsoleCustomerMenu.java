package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.customer.controller.CustomerConsoleController;
import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import com.programmers.voucher.global.util.ConsoleMessages;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Component
public class ConsoleCustomerMenu {
    private final Console console;
    private final CustomerConsoleController customerController;

    public ConsoleCustomerMenu(Console console, CustomerConsoleController customerController) {
        this.console = console;
        this.customerController = customerController;
    }

    public void runningCustomerService() {
        console.printCustomerCommandSet();
        boolean run = true;
        while (run) {
            run = customerMapping();
        }
    }

    private boolean customerMapping() {
        CustomerCommandType customerCommandType = console.inputCustomerCommandType();
        switch (customerCommandType) {
            case CREATE -> {
                CustomerCreateRequest request = console.inputCustomerCreateInfo();
                UUID customerId = customerController.createCustomer(request);
                String consoleMessage = MessageFormat.format(ConsoleMessages.CREATED_NEW_CUSTOMER, customerId);
                console.print(consoleMessage);
            }
            case LIST -> {
                List<CustomerDto> customers = customerController.findCustomers();
                console.printCustomers(customers);
            }
            case UPDATE -> {
                CustomerUpdateRequest request = console.inputCustomerUpdateInfo();
                customerController.updateCustomer(request);
                console.print(ConsoleMessages.UPDATED_CUSTOMER);
            }
            case DELETE -> {
                UUID customerId = console.inputUUID();
                customerController.deleteCustomer(customerId);
                console.print(ConsoleMessages.DELETED_CUSTOMER);
            }
            case BLACKLIST -> {
                List<CustomerDto> blacklistCustomers = customerController.findBlacklistCustomers();
                console.printCustomers(blacklistCustomers);
            }
            case HELP -> {
                console.printCustomerCommandSet();
            }
            case EXIT -> {
                return false;
            }
        }
        return true;
    }
}
