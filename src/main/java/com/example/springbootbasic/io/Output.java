package com.example.springbootbasic.io;

import com.example.springbootbasic.voucher.Voucher;

import java.util.List;

public interface Output {
    void printCommandList();
    void listAllVoucher(List<Voucher> vouchers);
}
