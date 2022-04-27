package com.blessing333.springbasic.domain.voucher.dto;

import com.blessing333.springbasic.domain.voucher.model.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class VoucherUpdateForm {
    private UUID voucherId;
    private Voucher.VoucherType voucherType;
    private long discountAmount;
}
