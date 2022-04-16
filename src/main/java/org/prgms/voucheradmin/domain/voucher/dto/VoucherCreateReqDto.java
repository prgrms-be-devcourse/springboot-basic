package org.prgms.voucheradmin.domain.voucher.dto;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

/**
 * 바우처를 생성하기 위해 입력된 값을 전달하는 DTO 클래스 입니다.
 **/
public class VoucherCreateReqDto {
    /**
     * 선택된 바우처의 종류를 의미하는 필드입니다.
     **/
    private VoucherType voucherType;
    /**
     * 바우처의 할인 값 또는 퍼센티지를 의미하는 필드입니다.
     **/
    private long amount;

    public VoucherCreateReqDto(VoucherType voucherType, long amount) {
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
