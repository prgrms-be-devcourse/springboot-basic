package com.dojinyou.devcourse.voucherapplication.voucher.domain;

import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherList {
    private final List<Voucher> voucherList;

    public VoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public VoucherResponseList toResponseList() {
        List<VoucherResponse> responseList = voucherList.stream().map(VoucherMapper::domainToResponseDto).collect(Collectors.toList());
        return new VoucherResponseList(responseList);
    }
}
