package org.prgms.voucherProgram.domain.program;

import org.prgms.voucherProgram.domain.menu.VoucherMenuType;
import org.prgms.voucherProgram.domain.voucher.VoucherType;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.exception.WrongCommandException;
import org.prgms.voucherProgram.exception.WrongDiscountAmountException;
import org.prgms.voucherProgram.exception.WrongDiscountPercentException;
import org.prgms.voucherProgram.service.VoucherService;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
import org.springframework.stereotype.Component;

@Component
public class VoucherProgram {
    private final VoucherService voucherService;
    private final InputView inputView;
    private final OutputView outputView;

    public VoucherProgram(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            VoucherMenuType voucherMenuType = inputMenu();
            switch (voucherMenuType) {
                case EXIT -> isNotEndProgram = false;
                case CREATE -> createVoucher();
                case LIST -> outputView.printVouchers(voucherService.findAllVoucher());
            }
        }
    }

    private void createVoucher() {
        VoucherType voucherType = inputVoucherType();
        VoucherDto voucherDto = inputView.inputVoucherInformation(voucherType.getNumber());
        while (true) {
            try {
                outputView.printVoucher(voucherService.create(voucherDto));
                return;
            } catch (WrongDiscountAmountException e) {
                outputView.printError(e.getMessage());
                voucherDto.setDiscountValue(inputView.inputDiscountAmount());
            } catch (WrongDiscountPercentException e) {
                outputView.printError(e.getMessage());
                voucherDto.setDiscountValue(inputView.inputDiscountPercent());
            }
        }
    }

    private VoucherType inputVoucherType() {
        while (true) {
            try {
                return VoucherType.findByNumber(inputView.inputVoucherType());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private VoucherMenuType inputMenu() {
        while (true) {
            try {
                return VoucherMenuType.from(inputView.inputVoucherMenu());
            } catch (WrongCommandException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
