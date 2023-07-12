package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(VoucherRequest voucherRequest) throws WrongRangeFormatException {
        return voucherService.createVoucher(voucherRequest);
    }

    public VoucherListResponse findAll() {
        return voucherService.findAll();
    }

    public VoucherListResponse getAssignedVoucherListByUsername(String username) {
        return voucherService.getAssignedVoucherListByUsername(username);
    }

    public VoucherListResponse getNotAssignedVoucherList() {
        return voucherService.getNotAssignedVoucher();
    }

    public VoucherListResponse getAssignedVoucherList() {
        return voucherService.getAssignedVoucherList();
    }
}
