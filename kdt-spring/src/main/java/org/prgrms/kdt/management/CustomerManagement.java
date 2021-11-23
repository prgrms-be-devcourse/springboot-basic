package org.prgrms.kdt.management;

import org.prgrms.kdt.domain.command.Command;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomerManagement implements Management{

    private final IO io;
    private final CustomerService customerService;

    @Autowired
    public CustomerManagement(@Qualifier("consoleIo") IO io, CustomerService customerService) {
        this.io = io;
        this.customerService = customerService;
    }

    public void run() throws RuntimeException, IOException {
        printInitMenu();
        String cmd = io.readLine().trim().toUpperCase();
        Command.validateCommand(cmd);

        Command command = Command.valueOf(cmd);
        execute(command);
    }

    public void execute(Command command) throws RuntimeException, IOException {
        command.doCustomerAction(customerService, io, command);
    }

    public void printInitMenu() throws IOException {
        io.writeLine("=== Customer Management ===");
        io.writeLine("Type exit to exit the program");
        io.writeLine("Type create to create a new voucher");
        io.writeLine("Type list to list all vouchers");
    }
}
