package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ErrorMessage;
import lombok.Getter;

import java.util.UUID;


@Getter
public class FixedDiscountVoucher extends Voucher {

    private final UUID voucherId;
    private final long discount;
    private final VoucherType voucherType;
    private UseStatusType useStatusType;

    public FixedDiscountVoucher(Long discount) {
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = UseStatusType.AVAILABLE;
    }

    public FixedDiscountVoucher(UUID voucherId, long discount, UseStatusType useStatusType){
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.FIXED;
        this.useStatusType = useStatusType;
    }
}
