package com.example.springbootbasic.voucher;

public interface Voucher {

    long discount(long beforeDiscount);

    String toString();
}
