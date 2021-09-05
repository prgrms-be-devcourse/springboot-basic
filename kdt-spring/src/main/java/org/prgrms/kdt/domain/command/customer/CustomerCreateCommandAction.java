package org.prgrms.kdt.domain.command.customer;

import org.prgrms.kdt.domain.command.CommandAction;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.dto.RequestCreateCustomerDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerCreateCommandAction implements CommandAction {

    private final CustomerService customerService;
    private final IO io;

    public CustomerCreateCommandAction(CustomerService customerService, IO io) {
        this.customerService = customerService;
        this.io = io;
    }

    @Override
    public void action() throws IOException, RuntimeException {
        printCreateMenu();

        String[] line = readAndSplitLine();

        validate(line);

        String name = line[0];
        String email = line[1];

        customerService.insert(name, email);

    }

    private void printCreateMenu() throws IOException {
        io.writeLine("=== Create Voucher ==");
        io.writeLine("Enter name of customer");
        io.writeLine("Enter email of customer");
        io.writeLine("Example: John jn@gmail.com");
        io.writeLine("=====================");
    }

    private void validate(String[] line) throws RuntimeException {
        if (isEmpty(line))
            throw new RuntimeException("Wrong Input");

        if (isInvalidEmail(line[1]))
            throw new RuntimeException("Wrong Input. email = " + line[1]);
    }

    private String[] readAndSplitLine() throws IOException {
        return io.readLine().trim().split(" ");
    }

    private boolean isEmpty(String[] line) {
        return line.length != 2;
    }

    private boolean isInvalidEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

}
