package com.wonu606.vouchermanager.service.factory.creator;

import com.wonu606.vouchermanager.domain.PercentageVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherVO;
import java.util.UUID;

public class PercentageVoucherCreator implements VoucherCreator {

    @Override
    public Voucher create(UUID uuid, double value) {
        return new PercentageVoucher(uuid, value);
    }
}
