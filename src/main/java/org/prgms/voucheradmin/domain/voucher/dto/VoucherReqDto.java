package org.prgms.voucheradmin.domain.voucher.dto;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.WrongInputException;

/**
 * 바우처를 생성하기 위해 입력된 값을 전달하는 DTO 클래스 입니다.
 **/
public class VoucherReqDto {
    /**
     * 선택된 바우처의 종류를 의미하는 필드입니다.
     **/
    private VoucherType voucherType;
    /**
     * 바우처의 할인 값 또는 퍼센티지를 의미하는 필드입니다.
     **/
    private long amount;

    public VoucherReqDto(VoucherType voucherType, long amount) {
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
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
