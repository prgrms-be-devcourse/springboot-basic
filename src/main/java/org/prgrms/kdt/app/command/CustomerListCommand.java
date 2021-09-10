package org.prgrms.kdt.app.command;

import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.service.CustomerService;
import org.springframework.stereotype.Component;


@Component
public class CustomerListCommand implements CommandOperator{
    private final CustomerService customerService;
    private final Console console;
    private final CommandType commandType;

    public CustomerListCommand(CustomerService customerService, Console console) {
        this.customerService = customerService;
        this.console = console;
        this.commandType = CommandType.CUSTOMER_LIST;
    }

    @Override
    public void execute() {
        var customers = customerService.getAllCustomers();
        console.printMessage("=== Customer List ===");
        if (!customers.isEmpty()) {
            customers.stream().map(Customer::toString).forEach(console::printMessage);
        } else {
            console.printMessage("No Voucher Data");
        }
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }

}
