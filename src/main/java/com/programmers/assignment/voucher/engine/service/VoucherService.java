package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.io.ConsoleInput;
import com.programmers.assignment.voucher.engine.io.ConsoleOutput;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.engine.voucher.FixedAmountVoucher;
import com.programmers.assignment.voucher.engine.voucher.PercentDiscountVoucher;
import com.programmers.assignment.voucher.engine.voucher.Voucher;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final ConsoleInput input;
    private final ConsoleOutput output;

    private final VoucherRepository voucherRepository;

    private static final String FIXED_VOUCHER_MESSAGE =
            "===Fixed amount voucher===\n" +
                    "Type FixedVoucher amount.";

    private static final String PERCENT_VOUCHER_MESSAGE =
            "===Percent discount voucher===\n" +
                    "Type PercentVoucher discount.";


    public VoucherService(ConsoleInput input, ConsoleOutput output, VoucherRepository voucherRepository) {
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

    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository
                        .findById(voucherId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        MessageFormat.format("Can not find a voucher for {0}", voucherId))
        );
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public void makeVoucher(String discountWay) {
        if (discountWay.equals(VoucherVariable.PERCENT.toString())) {
            makePercentVoucher();
            return;
        }
        if (discountWay.equals(VoucherVariable.FIXED.toString())) {
            makeFixedVoucher();
            return;
        }
    }
}
