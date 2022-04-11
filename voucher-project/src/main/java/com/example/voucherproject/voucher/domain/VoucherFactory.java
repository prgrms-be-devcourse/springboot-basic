package com.example.voucherproject.voucher.domain;

import com.example.voucherproject.common.enums.VoucherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.UUID;

import static com.example.voucherproject.common.enums.VoucherType.*;

@Slf4j
@Component
public class VoucherFactory {
    public Voucher create(VoucherType type){
        log.info("create " + type + " type voucher");
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