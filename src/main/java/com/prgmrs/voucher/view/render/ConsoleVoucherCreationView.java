package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.VoucherRequest;
import com.prgmrs.voucher.dto.VoucherResponse;
import com.prgmrs.voucher.enums.VoucherType;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleVoucherCreationView {
    private final VoucherController voucherController;
    private final BlacklistController blackListController;
    private final ConsoleReader consoleReader;
    private final ConsoleCreationWriter consoleCreationWriter;
    private final BlacklistProperties blacklistProperties;

    public ConsoleVoucherCreationView(VoucherController voucherController, BlacklistController blackListController, ConsoleReader consoleReader, ConsoleCreationWriter consoleCreationWriter, BlacklistProperties blacklistProperties) {
        this.voucherController = voucherController;
        this.blackListController = blackListController;
        this.consoleReader = consoleReader;
        this.consoleCreationWriter = consoleCreationWriter;
        this.blacklistProperties = blacklistProperties;
    }

    void selectVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showVoucherType();
            try {
                VoucherType voucherType = VoucherType.of(consoleReader.read());
                createVoucher(voucherType);
                continueRunning = false;
            } catch (NoSuchVoucherTypeException e) {
                consoleCreationWriter.write("such voucher type not exist");
            }
        }
    }

    void createVoucher(VoucherType voucherType) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showSpecificVoucherCreation(voucherType);
            String token = consoleReader.read();
            VoucherRequest voucherRequest = new VoucherRequest(voucherType, token);
            try {
                VoucherResponse voucherResponse = voucherController.createVoucher(voucherRequest);
                consoleCreationWriter.showVoucherResult(voucherResponse);
                continueRunning = false;
            } catch (WrongRangeFormatException e) {
                consoleCreationWriter.write("incorrect format or value out of range");
            }
        }
    }
}
