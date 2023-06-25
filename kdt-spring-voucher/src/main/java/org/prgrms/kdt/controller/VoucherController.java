package org.prgrms.kdt.controller;

import org.prgrms.kdt.database.VoucherDatabase;
import org.prgrms.kdt.input.UserInput;
import org.prgrms.kdt.input.VoucherCommand;
import org.prgrms.kdt.output.Output;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherController {
    private final VoucherDatabase voucherDatabase;
    private final Output output;
    private final UserInput userInput;

    @Autowired
    public VoucherController(VoucherDatabase voucherDatabase, Output output, UserInput userInput) {
        this.voucherDatabase = voucherDatabase;
        this.output = output;
        this.userInput = userInput;
    }

    public void createVoucher(VoucherCommand userInputVoucherCommand) {
        switch (userInputVoucherCommand) {
            case FIXED_AMOUNT -> {
                output.displayFixedAmountInputValue();
                output.displayUserInputLine();
                long amount = userInput.userInputVoucherValue();
                voucherDatabase.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(), amount));
            }
            case PERCENT_DISCOUNT -> {
                output.displayPercentDiscountInputValue();
                output.displayUserInputLine();
                long percent = userInput.userInputVoucherValue();
                voucherDatabase.saveVoucher(new PercentDiscountVoucher(UUID.randomUUID(), percent));
            }
            case WRONG -> output.userInputWrongValue();
        }
    }

    public void showVoucherList() {
        List<Voucher> voucherList = voucherDatabase.findAllVoucher();
        voucherList.stream().forEach(System.out::println);
    }
}
