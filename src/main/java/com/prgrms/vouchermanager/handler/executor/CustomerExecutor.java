package com.prgrms.vouchermanager.handler.executor;

import com.prgrms.vouchermanager.controller.CustomerController;
import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.exception.NotCorrectCommandException;
import com.prgrms.vouchermanager.exception.NotCorrectFormException;
import com.prgrms.vouchermanager.exception.NotCorrectIdException;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.io.ConsoleReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static com.prgrms.vouchermanager.message.ConsoleMessage.*;

@Component
public class CustomerExecutor {

    private final ConsoleReader consoleReader;
    private final ConsolePrint consolePrint;
    private final CustomerController controller;

    public CustomerExecutor(ConsoleReader consoleReader, ConsolePrint consolePrint, CustomerController controller) {
        this.consoleReader = consoleReader;
        this.consolePrint = consolePrint;
        this.controller = controller;
    }

    public void create() {
        consolePrint.printMessage(GET_CUSTOMER_NAME.getMessage());
        String name = consoleReader.readString();
        consolePrint.printMessage(GET_CUSTOMER_YEAR.getMessage());
        int year = 0;
        try {
            year = consoleReader.readInt();
        } catch (NumberFormatException e) {
            throw new NotCorrectFormException(String.valueOf(year));
        }

        controller.create(name, year);
        consolePrint.printMessage(COMPLETE_CREATE_VOUCHER.getMessage());
    }

    public void list() throws EmptyListException {
        List<Customer> customers = controller.list();
        if(customers.isEmpty()) throw new EmptyListException(customers);
        else consolePrint.printList(customers);
    }

    public void delete() {
        consolePrint.printMessage(GET_CUSTOMER_ID.getMessage());
        try {
            UUID id = UUID.fromString(consoleReader.readString());
            controller.delete(id);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectIdException();
        }
        consolePrint.printMessage(COMPLETE_DELETE_CUSTOMER.getMessage());
    }

    public void update() {
        consolePrint.printMessage(SELECT_UPDATE_TARGET.getMessage());
        String command = consoleReader.readString();
        try {
            switch (command) {
                case "name" -> {
                    consolePrint.printMessage(GET_CUSTOMER_ID.getMessage());
                    UUID id = UUID.fromString(consoleReader.readString());
                    consolePrint.printMessage(GET_CUSTOMER_NAME.getMessage());
                    String name = consoleReader.readString();
                    controller.updateName(id, name);
                }
                case "year" -> {
                    consolePrint.printMessage(GET_CUSTOMER_ID.getMessage());
                    UUID id = UUID.fromString(consoleReader.readString());
                    consolePrint.printMessage(GET_CUSTOMER_YEAR.getMessage());
                    int year = 0;
                    try {
                        year = consoleReader.readInt();
                        controller.updateYearOfBirth(id, year);
                    } catch (NumberFormatException e) {
                        throw new NotCorrectFormException(String.valueOf(year));
                    }
                }
                default -> throw new NotCorrectCommandException(command);
            }
        } catch (IllegalArgumentException e) {
            throw new NotCorrectIdException();
        }
        consolePrint.printMessage(COMPLETE_UPDATE_CUSTOMER.getMessage());
    }

    public void blackList() {
        List<Customer> customers = controller.findBlacklist();
        if(customers.isEmpty()) throw new EmptyListException(customers);
        else consolePrint.printList(controller.findBlacklist());
    }
}
