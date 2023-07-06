package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.customer.domain.Customer;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Voucher {

    private final UUID voucherId;
    private final VoucherPolicy voucherPolicy;
    private long voucherAmount;
    private Customer customer;

    public Voucher(VoucherPolicy voucherPolicy, long voucherAmount) {
        voucherPolicy.validateAmount(voucherAmount);
        this.voucherId = UUID.randomUUID();
        this.voucherPolicy = voucherPolicy;
        this.voucherAmount = voucherAmount;
    }

    public long discount(long beforeDiscount) {
        return voucherPolicy.discount(beforeDiscount, voucherAmount);
    }

    public void updateAmount(long voucherAmount) {
        voucherPolicy.validateAmount(voucherAmount);
        this.voucherAmount = voucherAmount;
    }

    public void assignVoucherToCustomer(Customer customer) {
        this.customer = customer;
    }
}

