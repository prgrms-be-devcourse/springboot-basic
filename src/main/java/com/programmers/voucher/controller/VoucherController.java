package com.programmers.voucher.controller;

import com.programmers.voucher.controller.voucher.dto.VoucherAssignRequest;
import com.programmers.voucher.io.Message;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.service.VoucherService;

import java.util.List;

public class VoucherController {
    private final VoucherService voucherService;
    private final Output output;

    public VoucherController(VoucherService voucherService, Output output) {
        this.voucherService = voucherService;
        this.output = output;
    }

    public void assign(VoucherAssignRequest voucherAssignRequest) {
        voucherService.assign(voucherAssignRequest.voucherId(), voucherAssignRequest.email());
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
