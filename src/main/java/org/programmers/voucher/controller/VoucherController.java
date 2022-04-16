package org.programmers.voucher.controller;

import org.programmers.voucher.domain.FixedAmountVoucher;
import org.programmers.voucher.domain.PercentDiscountVoucher;
import org.programmers.voucher.domain.Voucher;
import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.io.Input;
import org.programmers.voucher.io.Output;
import org.programmers.voucher.repository.VoucherRepository;
import org.programmers.voucher.util.Command;
import org.programmers.voucher.util.IllegalCommandException;
import org.programmers.voucher.util.IllegalVoucherTypeException;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class VoucherController implements Runnable {

    private final VoucherRepository voucherRepository;
    private final Input input;
    private final Output output;

    @Value("${FixedAmountVoucher.Amount}")
    private int amount;
    @Value("${PercentDiscountVoucher.Percent}")
    private int percent;

    public VoucherController(VoucherRepository voucherRepository, Input input, Output output) {
        this.voucherRepository = voucherRepository;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("=== Voucher Program ===");
        while(true) {
            output.listCommand();
            try {
                Command command = input.inputCommand();
                switch (command) {
                    case EXIT:
                        return;
                    case CREATE:
                        output.listVoucherType();
                        Voucher voucher = makeVoucher(input.inputVoucherType());
                        voucherRepository.save(voucher);
                        break;
                    case LIST:
                        output.listVoucher(voucherRepository.findAll());
                        break;
                }
            } catch (IllegalCommandException e) {
                System.out.println("Illegal Command");
            } catch (IllegalVoucherTypeException e) {
                System.out.println("Illegal Voucher Type");
            }
        }
    }

    private Voucher makeVoucher(VoucherType voucherType){
        return switch (voucherType) {
            case FixedAmountVoucher -> new FixedAmountVoucher(UUID.randomUUID(), amount);
            case PercentDiscountVoucher -> new PercentDiscountVoucher(UUID.randomUUID(), percent);
        };
    }
}
