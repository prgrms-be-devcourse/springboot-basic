package org.prgms.kdtspringvoucher;

import org.prgms.kdtspringvoucher.blackList.service.BlackListService;
import org.prgms.kdtspringvoucher.io.Input;
import org.prgms.kdtspringvoucher.io.Output;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineApplication implements Runnable{

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final BlackListService blackListService;
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Input input, Output output, VoucherService voucherService, BlackListService blackListService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.blackListService = blackListService;
    }

    @Override
    public void run() {
        while (true) {
            CommandType command = input.inputCommandType();
            if (!isCorrectCommandType(command)) continue;

            switch (command) {
                case EXIT -> System.exit(0);

                case LIST -> voucherService.showAllVoucher();

                case BLACK -> blackListService.showAllBlackList();

                case CREAT -> {
                    VoucherType voucherType = input.inputVoucherType();
                    if (!isCorrectVoucherType(voucherType)) continue;

                    Long amountOrPercent = input.inputDiscountAmountOrPercent();
                    voucherService.createVoucher(voucherType, amountOrPercent);
                }
            }
        }
    }

    private boolean isCorrectCommandType(CommandType command) {
        if (command == null) {
            logger.error("Not correct input command type....");
            output.commandTypeError();
            return false;
        }
        logger.info("Correct input command type => {}", command);
        return true;
    }

    private boolean isCorrectVoucherType(VoucherType voucherType) {
        if (voucherType == null) {
            logger.error("Not correct input voucher type....");
            output.voucherTypeError();
            return false;
        }
        logger.info("Correct input voucher type => {}", voucherType);
        return true;
    }
}
