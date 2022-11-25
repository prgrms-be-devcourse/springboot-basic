package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.VoucherAssignRequest;
import com.programmers.voucher.controller.dto.VoucherCreateRequest;
import com.programmers.voucher.controller.dto.VoucherUpdateRequest;
import com.programmers.voucher.io.Message;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.model.voucher.Voucher;
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

    public void create(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = voucherService.create(voucherCreateRequest);
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

    public void update(VoucherUpdateRequest voucherUpdateRequest) {
        Voucher updated = voucherService.update(voucherUpdateRequest.voucherId(), voucherUpdateRequest.discountValue());
        output.printVoucher(updated);
    }

    public void assign(VoucherAssignRequest voucherAssignRequest) {
        voucherService.assign(voucherAssignRequest.voucherId(), voucherAssignRequest.email());
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
