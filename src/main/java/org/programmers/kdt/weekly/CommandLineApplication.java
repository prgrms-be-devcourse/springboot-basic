package org.programmers.kdt.weekly;

import java.util.Optional;
import org.programmers.kdt.weekly.customer.CustomerService;
import org.programmers.kdt.weekly.io.Console;
import org.programmers.kdt.weekly.io.InputErrorType;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication {

    private final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    final String CREATE = "create";
    final String LIST = "list";
    final String EXIT = "exit";
    final String BLACK_LIST = "list -b";

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

    public void start() {
        var programExit = false;

        while (!programExit) {
            console.startMessage();
            String command = console.getUserInput();
            logger.info("user input -> {}", command);

            switch (command.toLowerCase()) {
                case CREATE:
                    console.voucherSelectMessage();
                    try {
                        int selectVoucherType = Integer.parseInt(console.getUserInput());
                        logger.info("user voucher select input -> {}", selectVoucherType);
                        VoucherType voucherType = VoucherType.findByNumber(selectVoucherType);
                        console.voucherDiscountMessage();
                        voucherService.createVoucher(voucherType,
                            Integer.parseInt(console.getUserInput()));
                        console.voucherCreateSucceedMessage();
                    } catch (IllegalArgumentException e) {
                        logger.error("number voucher type input error");
                        console.inputErrorMessage(InputErrorType.INVALID);
                    }
                    break;
                case LIST:
                    Optional.ofNullable(voucherService.voucherList())
                        .ifPresentOrElse((vouchers -> console.voucherListPrint(vouchers.get())),
                            () -> console.inputErrorMessage(InputErrorType.VOUCHER_EMPTY));
                    break;
                case BLACK_LIST:
                    Optional.ofNullable(customerService.BlackList())
                        .ifPresentOrElse((customer -> console.customerListPrint(customer.get())),
                            () -> console.inputErrorMessage(InputErrorType.BLACK_LIST_EMPTY));
                    break;
                case EXIT:
                    console.programExitMessage();
                    programExit = true;
                    break;
                default:
                    console.inputErrorMessage(InputErrorType.COMMAND);
                    break;
            }
            console.newLinePrint();
        }
    }
}