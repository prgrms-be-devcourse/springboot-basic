package org.prgrms.part1.engine;

import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.DomainType;
import org.prgrms.part1.engine.enumtype.VoucherType;
import org.prgrms.part1.engine.service.CustomerService;
import org.prgrms.part1.engine.service.VoucherService;
import org.prgrms.part1.exception.VoucherException;
import org.prgrms.part1.io.Input;
import org.prgrms.part1.io.Output;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class VoucherFunction {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Input input;
    private final Output output;
    private final Logger logger;

    public VoucherFunction(VoucherService voucherService, CustomerService customerService, Input input, Output output, Logger logger) {
        this.customerService = customerService;
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
        this.logger = logger;
    }

    public void showListMenu() {
        while (true) {
            var num = selectDomain();
            Optional<DomainType> domainType = DomainType.findMatchingCode(num);
            if (domainType.isEmpty()) {
                logger.debug("User select back to previous page");
                return;
            }
            domainType.get().showList(this);
        }
    }

    public void showVouchers() {
        if (voucherService.getAllVouchers().isEmpty()) {
            output.print("Voucher List is empty!");
            return;
        }

        output.print("Show Voucher List\n");
        voucherService.getAllVouchers().forEach(v -> output.print(v.toString() + "\n"));
    }

    public void showCustomers() {
        if (customerService.getAllCustomers().isEmpty()) {
            output.print("Customer List is empty!");
            return;
        }

        output.print("Show Customer List\n");
        customerService.getAllCustomers().forEach(c -> output.print(c.toString() + "\n"));
    }

    public void showCreateMenu() {
        while (true) {
            var num = selectDomain();
            Optional<DomainType> domainType = DomainType.findMatchingCode(num);
            if (domainType.isEmpty()) {
                logger.debug("User select back to previous page");
                return;
            }
            domainType.get().showCreate(this);
        }
    }

    public void showVoucherCreateMenu() {
        while (true) {
            var num = selectVoucherType();
            Optional<VoucherType> voucherType = VoucherType.findMatchingCode(num);
            if (voucherType.isEmpty()) {
                logger.debug("User select back to main menu");
                return;
            }
            var inputValue = input.inputQuestion("Type discount amount(percent) of Voucher : ");
            long value = parseValue(inputValue);
            Voucher voucher = voucherService.insertVoucher(voucherType.get().createVoucher(UUID.randomUUID(), value, LocalDateTime.now().withNano(0)));
            logger.info(MessageFormat.format("Create Voucher.\n{0}", voucher.toString()));
        }
    }

    public void createCustomer() {
        output.print("CREATE CUSTOMER\n");
        String name = input.inputQuestion("Name : ");
        String email = input.inputQuestion("Email : ");
        customerService.insertCustomer(customerService.createCustomer(name, email));
    }

    private String selectVoucherType() {
        output.print("Select type of Voucher");
        output.print("1. Fixed Amount Voucher");
        output.print("2. Percent Amount Voucher");
        output.print("Other. Back to prev page");
        return input.select();
    }

    private String selectDomain() {
        output.print("Select domain");
        output.print("1. Voucher");
        output.print("2. Customer");
        output.print("Other. Back to prev page");
        return input.select();
    }

    private long parseValue(String inputValue) {
        if (Pattern.matches("[\\d]+", inputValue)) {
            return Long.parseLong(inputValue);
        } else {
            throw new VoucherException("Please type invalid number.");
        }
    }
}
