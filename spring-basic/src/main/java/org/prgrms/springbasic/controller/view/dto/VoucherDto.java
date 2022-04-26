package org.prgrms.springbasic.controller.view.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;

import java.util.UUID;

@Getter
@Setter
public class VoucherDto {

    private UUID voucherId;

    private VoucherType voucherType;

    private long discountInfo;

    private UUID customerId;

    public VoucherDto() {}

    @Builder
    private VoucherDto(UUID voucherId, VoucherType voucherType, long discountInfo, UUID customerId) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountInfo = discountInfo;
        this.customerId = customerId;
    }

    public static VoucherDto updateVoucherDtoFrom(Voucher voucher) {
        return VoucherDto.builder().voucherId(voucher.getVoucherId()).
                voucherType(voucher.getVoucherType())
                .discountInfo(voucher.getDiscountInfo())
                .customerId(voucher.getCustomerId())
                .build();
    }

    public static VoucherDto assignVoucherDtoFrom(Voucher voucher) {
        return VoucherDto.builder().voucherId(voucher.getVoucherId()).customerId(voucher.getCustomerId()).build();
    }

}
