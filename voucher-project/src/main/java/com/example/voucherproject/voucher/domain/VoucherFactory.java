package com.example.voucherproject.voucher.domain;

import com.example.voucherproject.common.enums.VoucherType;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.example.voucherproject.common.enums.VoucherType.FIXED;
import static com.example.voucherproject.common.enums.VoucherType.PERCENT;

@Slf4j
public class VoucherFactory {
    public static Voucher create(VoucherType type){
        log.debug("create " + type + " type voucher");
        switch(type){
            case FIXED:
                return new FixedDiscountVoucher(UUID.randomUUID(), FIXED);
            case PERCENT:
                return new PercentDiscountVoucher(UUID.randomUUID(), PERCENT);
            default:
                log.error("정의되지 않은 Voucher 종류입니다");
                return null;
        }
    }
}