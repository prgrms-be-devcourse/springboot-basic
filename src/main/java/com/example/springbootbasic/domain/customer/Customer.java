package com.example.springbootbasic.domain.customer;

import com.example.springbootbasic.domain.voucher.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private long customerId;

    private CustomerStatus status;

    private List<Voucher> vouchers = new ArrayList<>();

    public Customer(long customerId) {
        this.customerId = customerId;
    }

    public Customer(CustomerStatus status) {
        this.status = status;
    }

    public Customer(long customerId, CustomerStatus status) {
        this.customerId = customerId;
        this.status = status;
    }

    public long getCustomerId() {
        return customerId;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public boolean isBlack() {
        return status.isBlack();
    }

    public boolean isNormal() { return status.isNormal(); }

    public void receiveFrom(Voucher voucher) {
        vouchers.add(voucher);
    }

    public boolean hasVoucher(Voucher findVoucher) {
        return vouchers.stream().anyMatch(voucher -> Objects.equals(voucher.getVoucherId(), findVoucher.getVoucherId()));
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", status=" + status +
                ", vouchers=" + vouchers +
                '}';
    }
}
