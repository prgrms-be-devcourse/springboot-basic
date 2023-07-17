package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public record VoucherListResponse(List<Voucher> voucherList) {
    @Override
    public String toString() {
        return voucherList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
