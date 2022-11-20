package com.programmers.voucher.controller;

import com.programmers.voucher.io.View;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherController {
    private final VoucherService voucherService;
    private final View view;

    public VoucherController(VoucherService voucherService, View view) {
        this.voucherService = voucherService;
        this.view = view;
    }

    public void createVoucher(VoucherType inputVoucherType, long inputDiscountValue) {
        Voucher voucher = voucherService.create(inputVoucherType, inputDiscountValue);
        view.printVoucher(voucher);
    }

    public void findAllVouchers() {
        checkEmptyVoucher(voucherService.findAllVoucher());
    }

    public void findVoucher(UUID inputVoucherId) {
        Voucher selected = voucherService.findById(inputVoucherId);
        view.printVoucher(selected);
    }

    public void updateVoucher(UUID inputVoucherId, long inputDiscountValue, VoucherType inputVoucherType) {
        Voucher updated = voucherService.update(inputVoucherId, inputDiscountValue, inputVoucherType);
        view.printVoucher(updated);
    }

    public void deleteAllVoucher() {
        voucherService.deleteAll();
        view.printDeleteAll();
    }

    private void checkEmptyVoucher(List<Voucher> vouchers) {
        if (vouchers.isEmpty() || vouchers.size() == 0) {
            view.printEmptyVouchers();
            return;
        }
        view.printVouchers(vouchers);
    }
}
