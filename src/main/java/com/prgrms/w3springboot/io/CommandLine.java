package com.prgrms.w3springboot.io;

import com.prgrms.w3springboot.voucher.service.VoucherService;

import java.util.UUID;

public class CommandLine implements Runnable {
    private final Console console;
    private final VoucherService voucherService;

    public CommandLine(final Console console, final VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            console.printInit();
            String commandType = console.input();
            switch (CommandType.of(commandType)) {
                case CREATE:
                    console.printTypeChoice();
                    String voucherType = console.input();
                    console.printDiscountAmountChoice();
                    long discountAmount = Long.parseLong(console.input());
                    UUID createdVoucherId = voucherService.createVoucher(voucherType, discountAmount);
                    if (createdVoucherId == null) {
                        console.printInvalidMessage();
                    }
                    console.printVoucher(createdVoucherId);
                    break;
                case LIST:
                    console.printVoucherList(voucherService.listVoucher());
                    break;
                case EXIT:
                    console.printExit();
                    flag = false;
                    break;
                default:
                    console.printInvalidMessage();
            }
        }
    }
}
