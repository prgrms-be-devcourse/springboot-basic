package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;

public class CreateCommandStrategy implements CommandStrategy {

    private static final Logger log = LoggerFactory.getLogger(CreateCommandStrategy.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CreateCommandStrategy(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void runCommand() {
        String chosenVoucher = input.chooseVoucher();
        if (chosenVoucher.equalsIgnoreCase(String.valueOf(VoucherType.FixedAmountVoucher))) {
            int amount = input.inputDiscount();
            voucherService.createVoucher(VoucherType.FixedAmountVoucher, amount, 0);
        }
        if (chosenVoucher.equalsIgnoreCase(String.valueOf(VoucherType.PercentDiscountVoucher))) {
            int percent = input.inputDiscount();
            voucherService.createVoucher(VoucherType.FixedAmountVoucher, 0, percent);
        }
        output.voucherCreated();
    }
}
