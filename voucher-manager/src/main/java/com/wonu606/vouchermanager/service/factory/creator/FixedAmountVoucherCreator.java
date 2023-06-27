package com.wonu606.vouchermanager.service.factory.creator;

import com.wonu606.vouchermanager.domain.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherVO;
import java.util.UUID;

public class FixedAmountVoucherCreator implements VoucherCreator {

    @Override
    public Voucher create(UUID uuid, VoucherVO voucherVO) {
        return new FixedAmountVoucher(uuid, voucherVO.getValue());
    }
}
