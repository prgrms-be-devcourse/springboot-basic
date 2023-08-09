package com.example.voucher.voucher.controller.model;

import java.util.Arrays;
import java.util.List;

import com.example.voucher.constant.ResponseStatus;
import com.example.voucher.voucher.service.dto.VoucherDTO;

public class VoucherResponse {

    private ResponseStatus status;
    private String errorMsg;
    private List<VoucherDTO> vouchers;

    public VoucherResponse(ResponseStatus status) {
        this.status = status;
    }

    public VoucherResponse(ResponseStatus status, List<VoucherDTO> vouchers) {
        this.status = status;
        this.vouchers = vouchers;
    }

    public VoucherResponse(ResponseStatus status, VoucherDTO voucher) {
        this.status = status;
        this.vouchers = Arrays.asList(voucher);
    }

    public VoucherResponse(ResponseStatus status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public List<VoucherDTO> getVouchers() {
        return vouchers;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
