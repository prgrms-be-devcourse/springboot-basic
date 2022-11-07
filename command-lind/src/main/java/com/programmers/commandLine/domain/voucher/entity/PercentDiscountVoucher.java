package com.programmers.commandLine.domain.voucher.entity;

import java.util.UUID;
/**
 *
 *  PercentDiscountVoucher의 설명을 여기에 작성한다.
 *  퍼센트로 할인률을 적용하는 바우처 입니다.
 *
 *  @author 장주영
 *  @version 1.0.0
 *  작성일 2022/11/03
 *
**/
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId = UUID.randomUUID();
    private final String percent;


    private final String type = "percentDiscountVoucher";

    public PercentDiscountVoucher(String percent) {
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Integer discount(Integer beforeDiscount) {
        return beforeDiscount * Integer.parseInt(percent) / 100;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDiscount() {
        return this.percent.toString() + "%";
    }

}
