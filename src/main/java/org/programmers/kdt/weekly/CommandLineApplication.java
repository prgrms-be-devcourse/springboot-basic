package org.programmers.kdt.weekly;

import java.util.List;
import org.programmers.kdt.weekly.customer.Customer;
import org.programmers.kdt.weekly.customer.CustomerService;
import org.programmers.kdt.weekly.io.Console;
import org.programmers.kdt.weekly.io.InputErrorType;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private static final String CREATE = "create";
    private static final String LIST = "list";
    private static final String EXIT = "exit";
    private static final String BLACK_LIST = "list -b";

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Console console;

    public CommandLineApplication(
        VoucherService voucherService,
        CustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    public void run() {
        var programExit = false;

        while (!programExit) {
            this.console.startMessage();
            String command = console.getUserInput();
            logger.info("user input -> {}", command);

            switch (command) {
                case CREATE -> {
                    this.console.voucherSelectMessage();
                    try {
                        int selectVoucherType = Integer.parseInt(console.getUserInput());
                        VoucherType voucherType = VoucherType.findByNumber(selectVoucherType);
                        this.console.voucherDiscountMessage();
                        voucherService.createVoucher(voucherType,
                            Integer.parseInt(console.getUserInput()));
                        this.console.voucherCreateSucceedMessage();
                    } catch (IllegalArgumentException e) {
                        logger.error("voucher number type input error", e);
                        this.console.inputErrorMessage(InputErrorType.INVALID);
                    }
                }
                case LIST -> {
                    List<Voucher> vouchers = voucherService.getVoucherList();
                    if (vouchers.size() > 0) {
                        this.console.voucherListPrint(vouchers);
                        break;
                    }
                    this.console.inputErrorMessage(InputErrorType.VOUCHER_EMPTY);
                }
                case BLACK_LIST -> {
                    List<Customer> customers = customerService.getBlackList();
                    if (customers.size() > 0) {
                        this.console.customerListPrint(customers);
                        break;
                    }
                    this.console.inputErrorMessage(InputErrorType.BLACK_LIST_EMPTY);
                }
                case EXIT -> {
                    this.console.programExitMessage();
                    programExit = true;
                }
                default -> this.console.inputErrorMessage(InputErrorType.COMMAND);
            }
            this.console.newLinePrint();
        }
    }
}