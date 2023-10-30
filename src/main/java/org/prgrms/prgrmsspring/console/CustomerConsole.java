package org.prgrms.prgrmsspring.console;

import org.prgrms.prgrmsspring.controller.CustomerController;
import org.prgrms.prgrmsspring.domain.command.Command;
import org.prgrms.prgrmsspring.domain.command.CustomerCommand;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class CustomerConsole implements ConsoleIOManager {

    @Autowired
    private CommandLineView commandLineView;
    @Autowired
    private CustomerController customerController;

    public void create() {
        Customer customer = customerController.create(commandLineView.createCustomer());
        commandLineView.print(customer);
    }

    public void update() {
        Customer updateCustomer = customerController.update(commandLineView.updateCustomer());
        commandLineView.print(updateCustomer);
    }

    public void delete() {
        UUID deleteCustomerId = commandLineView.deleteCustomer();
        customerController.delete(deleteCustomerId);
    }

    public void findAll() {
        List<Customer> allCustomers = customerController.findAll();
        commandLineView.printAll(allCustomers);
    }

    public void findAllBlackList() {
        List<Customer> allBlackList = customerController.findAllBlackList();
        commandLineView.printAll(allBlackList);
    }

    @Override
    public void run(String commandName) {
        Command command = Command.from(commandName, CustomerCommand.class);
        command.run(this);
    }

    @Override
    public void printCommands() {
        List<String> modeStrings = this.getModeStrings(CustomerCommand.values());
        commandLineView.printAll(modeStrings);
    }

}
