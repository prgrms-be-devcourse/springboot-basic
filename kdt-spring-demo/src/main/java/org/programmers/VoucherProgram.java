package org.programmers;

import org.programmers.console.Console;
import org.programmers.customer.model.CustomerInputType;
import org.programmers.voucher.model.VoucherInputType;
import org.programmers.console.ModeInputType;
import org.programmers.customer.model.Customer;
import org.programmers.customer.service.BlackListCustomerService;
import org.programmers.customer.service.CustomerService;
import org.programmers.voucher.model.VoucherBase;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.service.VoucherJdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class VoucherProgram implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherProgram.class);

    private final VoucherJdbcService voucherJdbcService;
    private final Console console;
    private final BlackListCustomerService blackListCustomerService;
    private final CustomerService customerService;


    public VoucherProgram(VoucherJdbcService voucherJdbcService, Console console, BlackListCustomerService blackListCustomerService, CustomerService customerService) {
        this.voucherJdbcService = voucherJdbcService;
        this.console = console;
        this.blackListCustomerService = blackListCustomerService;
        this.customerService = customerService;
    }

    public void voucherProgramRun() throws IOException {
        boolean flag = true;

        do {
            console.initInfoOutput();
            String modeInput = console.getInput();
            ModeInputType modeInputType = ModeInputType.getModeInputType(modeInput);

            switch (modeInputType) {
                case EXIT -> {
                    console.exitOutput();
                    flag = false;
                }

                case CUSTOMER -> {
                    console.customerInitInfoOutput();
                    CustomerInputType customerInputType = CustomerInputType.getInputType(console.getInput());
                    String mode = customerInputType.name();

                    switch (customerInputType) {
                        case EXIT -> console.exitModeOutput();

                        case CREATE -> createCustomer(mode);

                        case CUSTOMERLIST -> console.printCustomerList(customerService.getCustomerList());

                        case FIND -> findCustomer(mode);

                        case UPDATE -> updateCustomer(mode);

                        case DELETE -> deleteCustomer(mode);

                        case BLACKLIST -> console.printBlackList(blackListCustomerService.getBlackList());

                        default -> console.showInputError();
                    }
                }

                case VOUCHER -> {
                    console.voucherInitInfoOutput();
                    VoucherInputType voucherInputType = VoucherInputType.getInputType(console.getInput());
                    String mode = voucherInputType.name();

                    switch (voucherInputType) {
                        case EXIT -> console.exitModeOutput();

                        case CREATE -> createVoucher(mode);

                        case FIND -> findVoucher(mode);

                        case UPDATE -> updateVoucher(mode);

                        case DELETE -> deleteVoucher(mode);

                        case VOUCHERLIST -> console.printVoucherDataBaseList(voucherJdbcService.findAllVouchers());

                        default -> console.showInputError();
                    }
                }
                default -> console.showInputError();
            }
        } while (flag);
    }

    private void deleteVoucher(String mode) {
        console.voucherTypeInfoOutput(mode);
        String type = console.getInput();

        console.voucherNumberInfoOutput(mode);
        long value = console.getVoucherNumber();

        voucherJdbcService.deleteByVoucherTypeAndVoucherValue(VoucherType.getInputType(type), value);
    }

    private void updateVoucher(String mode) {
        console.voucherTypeInfoOutput(mode);
        String type = console.getInput();

        console.voucherNumberInfoOutput(mode);
        long value = console.getVoucherNumber();

        long changeValue = console.getVoucherNumber();

        voucherJdbcService.update(VoucherType.getInputType(type), value, changeValue);
    }

    private void findVoucher(String mode) {
        console.voucherTypeInfoOutput(mode);
        String voucherType = console.getInput();
        VoucherType inputVoucherType = VoucherType.getInputType(voucherType);

        console.voucherNumberInfoOutput(mode);
        Long value = console.getVoucherNumber();

        console.printFindVoucher(voucherJdbcService.findByVoucherTypeAndVoucherValue(inputVoucherType, value));
    }

    private void createVoucher(String mode) {
        console.voucherTypeInfoOutput(mode);
        String voucherType = console.getInput();
        VoucherType inputVoucherType = VoucherType.getInputType(voucherType);

        console.voucherNumberInfoOutput(mode);
        long number = Long.parseLong(String.valueOf(console.getVoucherNumber()));

        voucherJdbcService.create(new VoucherBase(UUID.randomUUID(), inputVoucherType, number, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
    }

    private void deleteCustomer(String mode) {
        console.customerEmailInfoOutput(mode);
        String email = console.getInput();

        customerService.deleteByEmail(email);
    }

    private void updateCustomer(String mode) {
        console.customerEmailInfoOutput(mode);
        String email = console.getInput();

        console.customerNameInfoOutput(mode);
        String name = console.getInput();

        customerService.update(email, name);
    }

    private void findCustomer(String mode) {
        console.customerEmailInfoOutput(mode);
        String email = console.getInput();

        Customer findCustomer = customerService.findByEmail(email);

        console.printCustomer(findCustomer);
    }

    private void createCustomer(String mode) {
        console.customerNameInfoOutput(mode);
        String name = console.getInput();

        console.customerEmailInfoOutput(mode);
        String email = console.getInput();

        customerService.create(new Customer(UUID.randomUUID(), name, email, null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
    }

    @Override
    public void run(String... args) throws IOException {
        voucherProgramRun();
    }
}
