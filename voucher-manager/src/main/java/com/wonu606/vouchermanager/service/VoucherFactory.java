package com.wonu606.vouchermanager.service;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import java.util.UUID;

public class VoucherFactory {

    public Voucher create(VoucherType type, UUID uuid, double discount) {
        return createVoucher(type, uuid, discount);
    }

    private Voucher createVoucher(VoucherType type, UUID uuid, double discount) {
        switch (type) {
            case FIXED:
                return new FixedAmountVoucher(uuid, discount);
            case PERCENT:
                return new PercentageVoucher(uuid, discount);
            default:
                throw new IllegalArgumentException("바우처 팩토리에서 생성할 수 없는 타입입니다.");
        }
    }
}
