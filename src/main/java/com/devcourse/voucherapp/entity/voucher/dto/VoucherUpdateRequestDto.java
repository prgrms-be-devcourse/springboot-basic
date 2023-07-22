package com.devcourse.voucherapp.entity.voucher.dto;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherUpdateRequestDto {

    private UUID id;
    private VoucherType type;
    private String discountAmount;
}
