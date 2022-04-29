package org.programmers.kdtspring;

import org.programmers.kdtspring.ConsoleIO.*;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.programmers.kdtspring.service.CustomerService;
import org.programmers.kdtspring.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VoucherManagement implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagement.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private Map<String, CommandStrategy> commandStrategy = new HashMap<>();

    public VoucherManagement(Input input, Output output, VoucherService voucherService, VoucherRepository voucherRepository, CustomerRepository customerRepository, CustomerService customerService) {
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
            commandStrategy.get(selectedOption).runCommand();
        }
    }

    private void putStrategy() {
        commandStrategy.put("exit", new ExitCommandStrategy(output));
        commandStrategy.put("create", new CreateCommandStrategy(input, output, voucherService));
        commandStrategy.put("list", new ListCommandStrategy(output, voucherService));
        commandStrategy.put("listForCustomer", new ListVoucherForCustomer(input, output, customerService));
    }
}