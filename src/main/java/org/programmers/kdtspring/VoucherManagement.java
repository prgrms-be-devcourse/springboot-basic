package org.programmers.kdtspring;

import org.programmers.kdtspring.ConsoleIO.*;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.programmers.kdtspring.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VoucherManagement implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagement.class);
    private Map<String, CommandStrategy> commandStrategy = new HashMap<>();

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;


    public VoucherManagement(Input input, Output output, VoucherService voucherService, VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
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
        commandStrategy.put("exit", new ExitCommandStrategy());
        commandStrategy.put("create", new CreateCommandStrategy(input, voucherService, customerRepository));
        commandStrategy.put("list", new ListCommandStrategy(output, voucherRepository));
        commandStrategy.put("listForCustomer", new ListVoucherForCustomer(input, voucherService, customerRepository));
    }
}