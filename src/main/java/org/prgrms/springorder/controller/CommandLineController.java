package org.prgrms.springorder.controller;

import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;
import org.prgrms.springorder.service.BlockCustomerService;
import org.prgrms.springorder.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineController {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);

    private final VoucherService voucherService;

    private final Console console;

    private final BlockCustomerService blockCustomerService;

    public CommandLineController(VoucherService voucherService,
        Console console,
        BlockCustomerService blockCustomerService) {
        this.voucherService = voucherService;
        this.console = console;
        this.blockCustomerService = blockCustomerService;
    }

    public void run() {
        while (ConsoleRunningStatus.isRunning()) {
            try {
                displayCommandGuide();

                String inputString = console.input();

                Command command = Command.of(inputString);

                execute(command);
            } catch (RuntimeException e) {
                logger.warn("errorName : {}, errorMessage : {}", e.getClass().getName(),
                    e.getMessage());
                console.showMessage(e.getMessage());
            }
        }
    }

    private void execute(Command command) { //
        switch (command) {
            case CREATE -> {
                VoucherCreateRequest voucherCreateRequest = getVoucherCreateRequest();

                console.showMessage(
                    "created Voucher. : " + voucherService.createVoucher(voucherCreateRequest));
            }

            case LIST -> console.showMessages(voucherService.findAllConvertedToString());

            case BLACKLIST -> console.showMessages(blockCustomerService.findAllConvertedToString());

            case EXIT -> ConsoleRunningStatus.stop();
        }
    }

    private void displayCommandGuide() {
        console.showMessages(new String[]{"=== Voucher Program ===",
            "Type 'exit' to exit the program. ",
            "Type 'create' to create a new voucher.",
            "Type 'list' to list all vouchers.",
            "Type 'black-list' to list all black list customers."}
        );
    }

    private VoucherCreateRequest getVoucherCreateRequest() {
        console.showMessage("select voucherType 'fixed' or 'percent' : ");
        String inputVoucherType = console.input();
        console.showMessage("input discount amount : ");
        long discountAmount = console.inputStringToLong();

        VoucherType voucherType = VoucherType.of(inputVoucherType);
        return VoucherCreateRequest.of(voucherType, discountAmount);
    }

}
