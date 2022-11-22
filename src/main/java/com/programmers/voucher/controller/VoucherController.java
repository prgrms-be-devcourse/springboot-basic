package com.programmers.voucher.controller;

import com.programmers.voucher.io.Message;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherController {
    private final VoucherService voucherService;
    private final Output output;

    public VoucherController(VoucherService voucherService, Output output) {
        this.voucherService = voucherService;
        this.output = output;
    }

    public void create(VoucherType inputVoucherType, long inputDiscountValue) {
        Voucher voucher = voucherService.create(inputVoucherType, inputDiscountValue);
        output.printVoucher(voucher);
    }

    public void findAll() {
        checkEmptyVoucher(voucherService.findAll());
    }

    public void findAllByCustomer(String email) {
        checkEmptyVoucher(voucherService.findAllByCustomer(email));
    }

    public void findById(UUID inputVoucherId) {
        Voucher selected = voucherService.findById(inputVoucherId);
        output.printVoucher(selected);
    }

    public void update(UUID inputVoucherId, long inputDiscountValue, VoucherType inputVoucherType) {
        Voucher updated = voucherService.update(inputVoucherId, inputDiscountValue, inputVoucherType);
        output.printVoucher(updated);
    }

    public void assign(UUID voucherId, String email) {
        voucherService.assign(voucherId, email);
    }

    public void deleteAll() {
        voucherService.deleteAll();
        output.print(Message.DELETE_ALL_VOUCHERS);
    }

    public void deleteByCustomer(String email) {
        voucherService.deleteByCustomer(email);
        output.print(Message.DELETE_ALL_VOUCHERS);
    }

    private void checkEmptyVoucher(List<Voucher> vouchers) {
        if (vouchers.isEmpty() || vouchers.size() == 0) {
            output.print(Message.EMPTY_VOUCHER_MESSAGE);
            return;
        }
        output.printVouchers(vouchers);
    }
}
