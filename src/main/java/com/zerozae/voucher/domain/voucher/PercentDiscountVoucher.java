package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ErrorMessage;
import lombok.Getter;

import java.util.UUID;


@Getter
public class PercentDiscountVoucher extends Voucher {

    private static final long ZERO = 0;

    private final UUID voucherId;
    private final long discount;
    private final VoucherType voucherType;
    private UseStatusType useStatusType;

    public PercentDiscountVoucher(Long discount) {
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = UseStatusType.AVAILABLE;
    }

    public PercentDiscountVoucher(UUID voucherId, Long discount, UseStatusType useStatusType){
        try {
            validateVoucherInfo(discount);
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = VoucherType.PERCENT;
        this.useStatusType = useStatusType;
    }
}
