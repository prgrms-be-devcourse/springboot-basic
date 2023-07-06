package com.devcourse.voucherapp.entity.dto;

import com.devcourse.voucherapp.entity.VoucherType;
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
