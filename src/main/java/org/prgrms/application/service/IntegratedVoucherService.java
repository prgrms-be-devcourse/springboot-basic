package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class IntegratedVoucherService extends VoucherService{
    public IntegratedVoucherService(VoucherRepository voucherRepository) {
        super(voucherRepository);
    }

    @Override
    public void createVoucher(VoucherType voucherType, double voucherDetail) {

    }
}
