package com.blessing333.springbasic.voucher.dto;

import com.blessing333.springbasic.voucher.domain.Voucher;
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
