package com.blessing333.springbasic.voucher.controller;

import com.blessing333.springbasic.voucher.domain.Voucher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class VoucherInformation implements Serializable {
    private final UUID voucherId;
    private final Voucher.VoucherType voucherType;
    private final long discountAmount;

    public static VoucherInformation fromEntity(Voucher voucher){
        return new VoucherInformation(voucher.getVoucherId(),
                                        voucher.getVoucherType(),
                                        voucher.getDiscountAmount()
        );
    }

    public static List<VoucherInformation> fromEntity(List<Voucher> vouchers){
        return vouchers.stream().map(VoucherInformation::fromEntity).collect(Collectors.toList());
    }
}
