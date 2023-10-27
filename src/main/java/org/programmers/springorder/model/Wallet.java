package org.programmers.springorder.model;

import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.voucher.Voucher;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private final Customer customer;
    private final List<Voucher> vouchers;

    public Wallet(Customer customer) {
        this.customer = customer;
        this.vouchers = new ArrayList<>();
    }

    public void addVoucher(Voucher voucher) {
        this.vouchers.add(voucher);
    }

}
