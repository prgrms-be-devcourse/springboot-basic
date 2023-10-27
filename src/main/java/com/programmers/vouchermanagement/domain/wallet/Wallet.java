package com.programmers.vouchermanagement.domain.wallet;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Wallet {
    private Integer id;
    private final Customer customer;
    private final Voucher voucher;
    private boolean used;

    public Wallet(Customer customer, Voucher voucher) {
        this.customer = customer;
        this.voucher = voucher;
        this.used = false;
    }

    public Wallet(Integer id, Customer customer, Voucher voucher, boolean used) {
        this.id = id;
        this.customer = customer;
        this.voucher = voucher;
        this.used = used;
    }
}
