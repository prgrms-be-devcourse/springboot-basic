package com.prgms.VoucherApp.domain.wallet.model;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.voucher.model.Voucher;

import java.util.UUID;

public class Wallet {
    UUID walletId;
    Customer customer;
    Voucher voucher;

    public Wallet(UUID walletId, Customer customer, Voucher voucher) {
        this.walletId = walletId;
        this.customer = customer;
        this.voucher = voucher;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Voucher getVoucher() {
        return voucher;
    }
}
