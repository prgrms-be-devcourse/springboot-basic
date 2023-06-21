package org.prgms.vouchermanagement.service;

import org.prgms.vouchermanagement.repository.FixedAmountVoucherRepository;
import org.prgms.vouchermanagement.repository.PercentDiscountVoucherRepository;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final FixedAmountVoucherRepository fixedAmountVoucherRepository;
    private final PercentDiscountVoucherRepository percentDiscountVoucherRepository;

    public VoucherService(FixedAmountVoucherRepository fixedAmountVoucherRepository, PercentDiscountVoucherRepository percentDiscountVoucherRepository) {
        this.fixedAmountVoucherRepository = fixedAmountVoucherRepository;
        this.percentDiscountVoucherRepository = percentDiscountVoucherRepository;
    }

    public void createNewVoucher(VoucherType voucherType) {

    }
}
