package com.devcourse.voucherapp.entity.voucher.response;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VouchersResponseDto {

    private final List<VoucherResponseDto> vouchers;

    public static VouchersResponseDto from(List<Voucher> vouchers) {

        return new VouchersResponseDto(vouchers.stream()
                .map(VoucherResponseDto::from)
                .toList());
    }
}
