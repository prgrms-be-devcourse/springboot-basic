package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.FixedAmountVoucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.repository.voucher.VoucherRepository;

import java.util.Random;

import static java.lang.Math.abs;

public class FixedVoucherService extends VoucherService{

    protected FixedVoucherService(VoucherRepository voucherRepository) {
        super(voucherRepository);
    }

    @Override
    public void createVoucher(VoucherType voucherType, double discountAmount) {
        long randomId = abs(new Random().nextLong());
        new FixedAmountVoucher(randomId,voucherType,discountAmount);
    }
}
