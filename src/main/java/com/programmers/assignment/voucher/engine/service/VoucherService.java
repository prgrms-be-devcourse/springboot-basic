package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.io.ConsoleInput;
import com.programmers.assignment.voucher.engine.io.ConsoleOutput;
import com.programmers.assignment.voucher.engine.model.Voucher;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final ConsoleInput input;
    private final ConsoleOutput output;

    private final VoucherRepository voucherRepository;

    private final CustomerService customerService;

    private static final String FIXED_VOUCHER_MESSAGE =
            "===Fixed amount voucher===\n" +
                    "Type FixedVoucher amount.";

    private static final String PERCENT_VOUCHER_MESSAGE =
            "===Percent discount voucher===\n" +
                    "Type PercentVoucher discount.";

    private static final String CUSTOMER_NAME_INPUT = "Type customer name";

    public VoucherService(ConsoleInput input, ConsoleOutput output, VoucherRepository voucherRepository, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.customerService = customerService;
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

    public void makeVoucher(String strCustomerId, String discountWay, String strDiscountValue) {
        var discountValue = Long.parseLong(strDiscountValue);
        var customerId = Long.parseLong(strCustomerId);
        System.out.println(customerId);
        var voucher = new Voucher(UUID.randomUUID(), VoucherVariable.chooseDiscountWay(discountWay), discountValue, customerId);
        voucherRepository.insert(voucher);
    }

    private long getDiscountValue(String discountWay) {
        if (discountWay.equals(VoucherVariable.PERCENT.toString())) {
            return Long.parseLong(input.inputVoucherInfo(PERCENT_VOUCHER_MESSAGE));
        }
        if (discountWay.equals(VoucherVariable.FIXED.toString())) {
            return Long.parseLong(input.inputVoucherInfo(FIXED_VOUCHER_MESSAGE));
        }
        throw new IllegalArgumentException(discountWay + " is wrong discountWay");
    }
}
