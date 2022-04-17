package com.prgms.management.voucher_wallet.entity;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.voucher.entity.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class VoucherWallet {
    private UUID id;
    private Customer customer;
    private Voucher voucher;

    public VoucherWallet(Customer customer, Voucher voucher) {
        this(UUID.randomUUID(), customer, voucher);
    }
}
