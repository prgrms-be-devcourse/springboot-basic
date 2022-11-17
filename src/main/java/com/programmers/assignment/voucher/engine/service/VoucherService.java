package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.io.Input;
import com.programmers.assignment.voucher.engine.io.Output;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.engine.voucher.FixedAmountVoucher;
import com.programmers.assignment.voucher.engine.voucher.PercentDiscountVoucher;
import com.programmers.assignment.voucher.engine.voucher.Voucher;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//@Service
public class VoucherService {

    private Input input;

    private Output output;
    private VoucherRepository voucherRepository;

    private final String FIXED_VOUCHER_MESSAGE =
            "===Fixed amount voucher===\n" +
                    "Type FixedVoucher amount.";

    private final String PERCENT_VOUCHER_MESSAGE =
            "===Percent discount voucher===\n" +
                    "Type PercentVoucher discount.";


    public VoucherService(Input input, Output output, VoucherRepository voucherRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
    }


    public void makeFixedVoucher() {
        var amount = Long.parseLong(input.inputVoucherInfo(FIXED_VOUCHER_MESSAGE));
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.insert(voucher);
    }

    public void makePercentVoucher() {
        var percent = Long.parseLong(input.inputVoucherInfo(PERCENT_VOUCHER_MESSAGE));
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.insert(voucher);
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return Optional.of(
                voucherRepository
                        .findById(voucherId)
                        .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)))
        );
    }

    public Map<UUID, Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
