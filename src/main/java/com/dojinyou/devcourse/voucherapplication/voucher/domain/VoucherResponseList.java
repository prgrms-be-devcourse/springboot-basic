package com.dojinyou.devcourse.voucherapplication.voucher.domain;

import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;

import java.util.List;

public class VoucherResponseList {
    private static final String MESSAGE_NO_ELEMENT = "조회된 Voucher가 없습니다.";
    private final List<VoucherResponse> voucherList;

    public VoucherResponseList(List<VoucherResponse> voucherList) {
        this.voucherList = voucherList;
    }

    @Override
    public String toString() {
        if (voucherList.size() == 0) {
            return MESSAGE_NO_ELEMENT;
        }
        StringBuilder sb = new StringBuilder();
        voucherList.forEach((voucherResponse)->sb.append(voucherResponse.toString()));
        return sb.toString();
    }
}
