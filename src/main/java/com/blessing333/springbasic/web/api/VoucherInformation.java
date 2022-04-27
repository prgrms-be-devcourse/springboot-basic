package com.blessing333.springbasic.web.api;

import com.blessing333.springbasic.domain.voucher.model.Voucher;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@XStreamAlias("Voucher")
public class VoucherInformation{
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
