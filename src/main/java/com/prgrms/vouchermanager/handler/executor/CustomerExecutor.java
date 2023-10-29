package com.prgrms.vouchermanager.handler.executor;

import com.prgrms.vouchermanager.controller.CustomerController;
import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.exception.NotCorrectCommand;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectId;
import com.prgrms.vouchermanager.io.ConsolePrint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class CustomerExecutor {

    private final ConsolePrint consolePrint;
    private final CustomerController controller;
    private final Scanner sc = new Scanner(System.in);

    public CustomerExecutor(ConsolePrint consolePrint, CustomerController controller) {
        this.consolePrint = consolePrint;
        this.controller = controller;
    }

    public void create() {
        consolePrint.printGetCustomerName();
        String name = sc.nextLine();
        consolePrint.printGetCustomerYear();
        int year = 0;
        try {
            year = sc.nextInt();
            sc.nextLine();
        } catch (NumberFormatException e) {
            throw new NotCorrectForm(String.valueOf(year));
        }

        controller.create(name, year);
        consolePrint.printCompleteCreate();
    }

    public void list() throws EmptyListException {
        List<Customer> customers = controller.list();
        if(customers.isEmpty()) throw new EmptyListException(customers);
        else consolePrint.printCustomerList(customers);
    }

    public void delete() {
        consolePrint.printGetCustomerId();
        try {
            UUID id = UUID.fromString(sc.nextLine());
            controller.delete(id);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectId();
        }
        consolePrint.printCompleteDelete();
    }

    public void update() {
        consolePrint.printUpdateSelect();
        String command = sc.nextLine();
        try {
            switch (command) {
                case "name" -> {
                    consolePrint.printGetCustomerId();
                    UUID id = UUID.fromString(sc.nextLine());
                    consolePrint.printGetCustomerName();
                    String name = sc.nextLine();
                    controller.updateName(id, name);
                }
                case "year" -> {
                    consolePrint.printGetCustomerId();
                    UUID id = UUID.fromString(sc.nextLine());
                    consolePrint.printGetCustomerYear();
                    int year = 0;
                    try {
                        year = sc.nextInt();
                        sc.nextLine();
                        controller.updateYearOfBirth(id, year);
                    } catch (NumberFormatException e) {
                        throw new NotCorrectForm(String.valueOf(year));
                    }
                }
                default -> throw new NotCorrectCommand(command);
            }
        } catch (IllegalArgumentException e) {
            throw new NotCorrectId();
        }
        consolePrint.printCompleteUpdate();
    }

    public void blackList() throws EmptyListException {
        List<Customer> customers = controller.findBlacklist();
        if(customers.isEmpty()) throw new EmptyListException(customers);
        else consolePrint.printBlacklist(controller.findBlacklist());
    }
}
