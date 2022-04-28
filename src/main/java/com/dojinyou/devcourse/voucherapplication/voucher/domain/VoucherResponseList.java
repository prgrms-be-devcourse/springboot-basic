package com.dojinyou.devcourse.voucherapplication.voucher.domain;

import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;

import java.util.List;

public class VoucherResponseList {
    private final List<VoucherResponse> voucherList;

    protected VoucherResponseList(List<VoucherResponse> voucherList) {
        this.voucherList = voucherList;
    }
}
