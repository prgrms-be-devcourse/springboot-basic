package com.example.springbootbasic.io;

import com.example.springbootbasic.voucher.Voucher;

import java.util.List;

public interface Output {
    void printCommand();
    void listAllVoucher(List<Voucher> vouchers);
}
