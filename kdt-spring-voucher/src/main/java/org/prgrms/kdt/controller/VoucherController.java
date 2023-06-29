package org.prgrms.kdt.controller;

import org.prgrms.kdt.input.UserInput;
import org.prgrms.kdt.input.VoucherCommand;
import org.prgrms.kdt.output.Output;
import org.prgrms.kdt.storage.VoucherStorage;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherController {
    private final VoucherStorage voucherStorage;
    private final Output output;
    private final UserInput userInput;

    @Autowired
    public VoucherController(VoucherStorage voucherStorage, Output output, UserInput userInput) {
        this.voucherStorage = voucherStorage;
        this.output = output;
        this.userInput = userInput;
    }

    public void createVoucher(VoucherCommand userInputVoucherCommand) {
        switch (userInputVoucherCommand) {
            case FIXED_AMOUNT -> {
                output.displayFixedAmountInputValue();
                output.displayUserInputLine();
                invalidFixedVoucherValueException();
            }
            case PERCENT_DISCOUNT -> {
                output.displayPercentDiscountInputValue();
                output.displayUserInputLine();
                invalidPercentVoucherValueException();
            }
            case WRONG -> output.userInputWrongValue();
        }
    }

    private void invalidFixedVoucherValueException() {
        try {
            long amount = userInput.userInputVoucherValue();
            if (amount < 0) {
                throw new IllegalArgumentException("Please enter a positive number");
            }
            voucherStorage.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(), amount));
        } catch (IllegalArgumentException e) {
            output.displayError(e);
        }
    }

    private void invalidPercentVoucherValueException() {
        try {
            long percent = userInput.userInputVoucherValue();
            if (percent < 0 || percent > 100) {
                throw new IllegalArgumentException("Please enter 0 to 100");
            }
            voucherStorage.saveVoucher(new PercentDiscountVoucher(UUID.randomUUID(), percent));
        } catch (IllegalArgumentException e) {
            output.displayError(e);
        }
    }
}
