package com.example.voucher_manager.io;

import com.example.voucher_manager.domain.service.VoucherService;
import com.example.voucher_manager.domain.voucher.Voucher;
import com.example.voucher_manager.domain.voucher.VoucherType;

import java.util.List;

public class CommandOperator {
    private final VoucherService voucherService;

    public CommandOperator(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher create(VoucherType voucherType, Long discountInfomation) {
        return voucherService.createVoucher(voucherType, discountInfomation);
    }

    public List<Voucher> getVoucherList() {
        return voucherService.findAll();
    }
}
