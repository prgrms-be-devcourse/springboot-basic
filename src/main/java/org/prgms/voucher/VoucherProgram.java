package org.prgms.voucher;

import org.prgms.voucher.entity.MenuType;
import org.prgms.voucher.entity.Voucher;
import org.prgms.voucher.entity.VoucherType;
import org.prgms.voucher.service.VoucherService;
import org.prgms.voucher.view.Console;
import org.prgms.voucher.view.InputView;
import org.prgms.voucher.view.OutputView;
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
            MenuType menuType = inputMenu();
            switch (menuType) {
                case EXIT:
                    isNotEndProgram = false;
                    break;
                case LIST:
                    outputView.printAllVoucher(voucherService.findAllVoucher());
                    break;
                case CREATE:
                    VoucherType voucherType = inputVoucherCommand();
                    Voucher voucher = createVoucher(voucherType);
                    outputView.printVoucher(voucher);
            }
        }
    }

    private Voucher createVoucher(VoucherType voucherType) {
        try {
            long discountValue = inputView.inputDiscountValue(voucherType);
            return voucherService.create(voucherType, discountValue);
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
        return createVoucher(voucherType);
    }

    private VoucherType inputVoucherCommand() {
        try {
            return VoucherType.findByCommand(inputView.inputVoucherCommand());
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
        return inputVoucherCommand();
    }

    private MenuType inputMenu() {
        try {
            return MenuType.of(inputView.inputMenu());
        } catch (Exception e) {
            outputView.printError(e.getMessage());
        }
        return inputMenu();
    }
}
