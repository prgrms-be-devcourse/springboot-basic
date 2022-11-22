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

    public void create(VoucherType inputVoucherType, long inputDiscountValue) {
        Voucher voucher = voucherService.create(inputVoucherType, inputDiscountValue);
        view.printVoucher(voucher);
    }

    public void findAll() {
        checkEmptyVoucher(voucherService.findAll());
    }

    public void findAllByCustomer(String email) {
        checkEmptyVoucher(voucherService.findAllByCustomer(email));
    }

    public void findById(UUID inputVoucherId) {
        Voucher selected = voucherService.findById(inputVoucherId);
        view.printVoucher(selected);
    }

    public void update(UUID inputVoucherId, long inputDiscountValue, VoucherType inputVoucherType) {
        Voucher updated = voucherService.update(inputVoucherId, inputDiscountValue, inputVoucherType);
        view.printVoucher(updated);
    }

    public void assign(UUID voucherId, String email) {
        voucherService.assign(voucherId, email);
    }

    public void deleteAll() {
        voucherService.deleteAll();
        view.printDeleteAll();
    }

    public void deleteByCustomer(String email) {
        voucherService.deleteByCustomer(email);
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
