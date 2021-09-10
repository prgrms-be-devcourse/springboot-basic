package org.prgrms.kdt.app.command;

import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.model.Customer;
import org.prgrms.kdt.service.CustomerService;
import org.springframework.stereotype.Component;


@Component
public class BlackCustomerListCommand implements CommandOperator{
    private final CustomerService customerService;
    private final Console console;
    private final CommandType commandType;

    public BlackCustomerListCommand(CustomerService customerService, Console console) {
        this.customerService = customerService;
        this.console = console;
        this.commandType = CommandType.BLACK_LIST;
    }

    @Override
    public void execute() {
        var customers = customerService.getAllBlackCustomers();
        console.printMessage("=== Black Customer List ===");
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
