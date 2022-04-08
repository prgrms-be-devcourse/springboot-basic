package org.programmers.kdt.weekly.voucher.service;

import org.programmers.kdt.weekly.io.Input;
import org.programmers.kdt.weekly.io.InputErrorType;
import org.programmers.kdt.weekly.io.Output;
import org.programmers.kdt.weekly.voucher.model.FixedAmountVoucher;
import org.programmers.kdt.weekly.voucher.model.PercentDiscountVoucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherCreateService {
    final String FIXED_VOUCHER = "fixedamountvoucher";
    final String PERCENT_VOUCHER = "percentdiscountvoucher";

    private final Input inputConsole;
    private final Output outputConsole;
    private final VoucherRepository voucherRepository;

    public VoucherCreateService(VoucherRepository voucherRepository, Output outputConsole, Input inputConsole) {
        this.voucherRepository = voucherRepository;
        this.outputConsole = outputConsole;
        this.inputConsole = inputConsole;
    }

    public void create() {
        outputConsole.voucherSelectMessage();
        String voucherType = inputConsole.getUserInput().toLowerCase();
        if (voucherType.equals("1") || voucherType.equals(FIXED_VOUCHER)) {
            fixedAmountVoucherCreate();
        } else if (voucherType.equals("2") || voucherType.equals(PERCENT_VOUCHER)) {
            percentDiscountVoucherCreate();
        } else {
            outputConsole.inputErrorMessage(InputErrorType.COMMAND);
        }
    }

    private void fixedAmountVoucherCreate() {
        outputConsole.voucherDiscountMessage(VoucherType.FixedVoucherRepository);
        String inputDiscount = inputConsole.getUserInput();
        if (isStrNum(inputDiscount)) {
            UUID voucherId = UUID.randomUUID();
            voucherRepository.insert(voucherId, new FixedAmountVoucher(voucherId, Integer.parseInt(inputDiscount)));
            System.out.println("Created !!");
        } else {
            outputConsole.inputErrorMessage(InputErrorType.INVALID);
        }
    }

    private void percentDiscountVoucherCreate() {
        outputConsole.voucherDiscountMessage(VoucherType.PercentDiscountVoucher);
        String inputDiscount = inputConsole.getUserInput();
        if (isStrNum(inputDiscount) && Long.parseLong(inputDiscount) <= 100) {
            UUID voucherId = UUID.randomUUID();
            voucherRepository.insert(voucherId, new PercentDiscountVoucher(voucherId,Integer.parseInt(inputDiscount)));
        } else {
            outputConsole.inputErrorMessage(InputErrorType.INVALID);
        }
    }

    private boolean isStrNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
