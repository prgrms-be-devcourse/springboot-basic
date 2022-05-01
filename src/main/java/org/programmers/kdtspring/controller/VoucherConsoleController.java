package org.programmers.kdtspring.controller;

import org.programmers.kdtspring.ConsoleIO.*;
import org.programmers.kdtspring.service.CustomerService;
import org.programmers.kdtspring.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.programmers.kdtspring.ConsoleIO.CommandType.*;

@Component
public class VoucherConsoleController implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VoucherConsoleController.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private Map<CommandType, CommandStrategy> commandStrategy = new HashMap<>();

    public VoucherConsoleController(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        logger.info("Voucher Application run...");
        putStrategy();
        while (true) {
            String selectedOption = input.showOption();
            commandStrategy.get(Enum.valueOf(CommandType.class, selectedOption)).runCommand();
        }
    }

    private void putStrategy() {
        commandStrategy.put(EXIT, new ExitCommandStrategy(output));
        commandStrategy.put(CREATE, new CreateCommandStrategy(input, output, voucherService));
        commandStrategy.put(LIST, new ListCommandStrategy(output, voucherService));
        commandStrategy.put(LIST_FOR_CUSTOMER, new ListVoucherForCustomer(input, output, customerService));
    }
}