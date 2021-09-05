package org.programmers;

import org.programmers.console.Console;
import org.programmers.customer.model.CustomerInputType;
import org.programmers.voucher.model.VoucherInputType;
import org.programmers.console.ModeInputType;
import org.programmers.customer.model.Customer;
import org.programmers.customer.service.BlackListCustomerService;
import org.programmers.customer.service.CustomerService;
import org.programmers.voucher.model.Voucher;
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
                    String initInput = console.getInput();
                    CustomerInputType customerInputType = CustomerInputType.getInputType(initInput);
                    String mode = customerInputType.name();
                    switch (customerInputType) {
                        case EXIT -> console.exitModeOutput();

                        case CREATE -> {
                            console.customerNameInfoOutput(mode);
                            String name = console.getInput();

                            console.customerEmailInfoOutput(mode);
                            String email = console.getInput();

                            customerService.create(new Customer(UUID.randomUUID(), name, email, null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
                        }

                        case CUSTOMERLIST -> console.printCustomerList(customerService.getCustomerList());

                        case FIND -> {
                            console.customerEmailInfoOutput(mode);
                            String email = console.getInput();

                            Customer findCustomer = customerService.findByEmail(email);

                            console.printCustomer(findCustomer);
                        }

                        case UPDATE -> {
                            console.customerEmailInfoOutput(mode);
                            String email = console.getInput();

                            console.customerNameInfoOutput(mode);
                            String name = console.getInput();

                            customerService.update(email, name);
                        }

                        case DELETE -> {
                            console.customerEmailInfoOutput(mode);
                            String email = console.getInput();

                            customerService.deleteByEmail(email);
                        }

                        case BLACKLIST -> console.printBlackList(blackListCustomerService.getBlackList());

                        default -> console.showInputError();
                    }
                }

                case VOUCHER -> {
                    console.voucherInitInfoOutput();

                    String initInput = console.getInput();
                    VoucherInputType voucherInputType = VoucherInputType.getInputType(initInput);
                    String mode = voucherInputType.name();
                    switch (voucherInputType) {
                        case EXIT -> console.exitModeOutput();

                        case CREATE -> {
                            console.voucherTypeInfoOutput(mode);
                            String voucherType = console.getInput();
                            VoucherType inputVoucherType = VoucherType.getInputType(voucherType);

                            console.voucherNumberInfoOutput(mode);
                            long number = Long.parseLong(String.valueOf(console.getVoucherNumber()));

                            voucherJdbcService.create(new Voucher(UUID.randomUUID(), inputVoucherType, number, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
                        }

                        case FIND -> {
                            console.voucherTypeInfoOutput(mode);
                            String voucherType = console.getInput();
                            VoucherType inputVoucherType = VoucherType.getInputType(voucherType);

                            console.voucherNumberInfoOutput(mode);
                            Long value = console.getVoucherNumber();

                            console.printFindVoucher(voucherJdbcService.findByVoucherTypeAndVoucherValue(inputVoucherType, value));
                        }

                        case UPDATE -> {
                            console.voucherTypeInfoOutput(mode);
                            String type = console.getInput();

                            console.voucherNumberInfoOutput(mode);
                            long value = console.getVoucherNumber();

                            long changeValue = console.getVoucherNumber();

                            voucherJdbcService.update(VoucherType.getInputType(type), value, changeValue);
                        }

                        case DELETE -> {
                            console.voucherTypeInfoOutput(mode);
                            String type = console.getInput();

                            console.voucherNumberInfoOutput(mode);
                            long value = console.getVoucherNumber();

                            voucherJdbcService.deleteByVoucherTypeAndVoucherValue(VoucherType.getInputType(type), value);
                        }

                        case VOUCHERLIST -> console.printVoucherDataBaseList(voucherJdbcService.findAllVouchers());

                        default -> console.showInputError();
                    }
                }
                default -> console.showInputError();
            }
        } while (flag);
    }

    @Override
    public void run(String... args) throws IOException {
        voucherProgramRun();
    }
}
