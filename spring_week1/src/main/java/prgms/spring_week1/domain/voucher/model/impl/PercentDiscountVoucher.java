package prgms.spring_week1.domain.voucher.model.impl;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.Voucher;

import java.util.UUID;


public class PercentDiscountVoucher implements Voucher {

    @Override
    public UUID getVoucherId() {
        return UUID.randomUUID();
    }


}
