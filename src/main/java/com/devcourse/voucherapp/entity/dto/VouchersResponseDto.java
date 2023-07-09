package com.devcourse.voucherapp.entity.dto;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.List;
import lombok.Getter;

@Getter
public class VouchersResponseDto {

    private final List<VoucherResponseDto> vouchers;

    public VouchersResponseDto(List<Voucher> vouchers) {
        this.vouchers = vouchers.stream()
                .map(VoucherResponseDto::new)
                .toList();
    }
}
