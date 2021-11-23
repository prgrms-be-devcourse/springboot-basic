package org.prgrms.kdt.domain.command.customer;

import org.prgrms.kdt.domain.command.CommandAction;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.CustomerService;

import java.io.IOException;
import java.text.MessageFormat;

public class CustomerListCommandAction implements CommandAction {

    private final CustomerService customerService;
    private final IO io;

    public CustomerListCommandAction(CustomerService customerService, IO io) {
        this.customerService = customerService;
        this.io = io;
    }

    @Override
    public void action() throws IOException, RuntimeException {
        printCustomers();
    }

    private void printCustomers() throws IOException {
        for (Customer customer : customerService.customers()) {
            printCustomer(customer);
        }
    }

    private void printCustomer(Customer customer) throws IOException {
        io.writeLine(MessageFormat.format("Name: {0} | Email: {1} | Created: {2}", customer.getName(), customer.getEmail(), customer.getCreatedAt()));
    }
}
