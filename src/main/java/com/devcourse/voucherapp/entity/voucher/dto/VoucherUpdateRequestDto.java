package com.devcourse.voucherapp.entity.voucher.dto;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoucherUpdateRequestDto {

    private final UUID id;
    private final VoucherType type;
    private final String discountAmount;
}
