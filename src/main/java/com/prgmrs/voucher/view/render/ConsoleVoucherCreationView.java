package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.VoucherRequest;
import com.prgmrs.voucher.dto.VoucherResponse;
import com.prgmrs.voucher.enums.VoucherSelectionType;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleVoucherCreationView {
    private final VoucherController voucherController;
    private final ConsoleReader consoleReader;
    private final ConsoleCreationWriter consoleCreationWriter;

    public ConsoleVoucherCreationView(VoucherController voucherController, ConsoleReader consoleReader, ConsoleCreationWriter consoleCreationWriter) {
        this.voucherController = voucherController;
        this.consoleReader = consoleReader;
        this.consoleCreationWriter = consoleCreationWriter;
    }

    void selectVoucher() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showVoucherType();
            try {
                VoucherSelectionType voucherSelectionType = VoucherSelectionType.of(consoleReader.read());
                createVoucher(voucherSelectionType);
                continueRunning = false;
            } catch (NoSuchVoucherTypeException e) {
                consoleCreationWriter.write("such voucher type not exist");
            }
        }
    }

    void createVoucher(VoucherSelectionType voucherSelectionType) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showSpecificVoucherCreation(voucherSelectionType);
            String token = consoleReader.read();
            VoucherRequest voucherRequest = new VoucherRequest(voucherSelectionType, token);
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
