package org.prgms.voucheradmin.domain.voucher.dto;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.customexception.WrongInputException;

public class VoucherUpdateReqDto {
    private UUID voucherId;
    private VoucherType voucherType;
    private long amount;

    public VoucherUpdateReqDto(UUID voucherId, VoucherType voucherType, long amount) {
        switch (voucherType) {
            case FIXED_AMOUNT:
                if(amount < 0)  {
                    throw new WrongInputException("0 <= amount");
                }
                break;
            case PERCENTAGE_DISCOUNT:
                if(amount < 0 || amount > 100)  {
                    throw new WrongInputException("0 <= amount <= 100");
                }
                break;
            default:
                throw new WrongInputException("wrong voucher type");
        }
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
