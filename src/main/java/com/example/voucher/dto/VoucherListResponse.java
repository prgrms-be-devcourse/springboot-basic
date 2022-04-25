package com.example.voucher.dto;

import com.example.voucher.domain.voucher.Voucher;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VoucherListResponse {
    private final List<VoucherResponse> voucherResponseList;

    public VoucherListResponse(List<VoucherResponse> voucherResponseList) {
        this.voucherResponseList = voucherResponseList;
    }

    public static VoucherListResponse from(List<Voucher> vouchers) {
        List<VoucherResponse> voucherResponseList = vouchers.stream()
                .map((v) -> VoucherResponse.from(v))
                .collect(Collectors.toList());
        return new VoucherListResponse(voucherResponseList);
    }

    @Override
    public String toString() {
        return "VoucherListResponse{" +
                "voucherResponseList=" + voucherResponseList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherListResponse that = (VoucherListResponse) o;
        return Objects.equals(voucherResponseList, that.voucherResponseList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherResponseList);
    }
}
