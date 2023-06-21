package org.prgms.vouchermanagement.service;

import org.prgms.vouchermanagement.repository.FixedAmountVoucherRepository;
import org.prgms.vouchermanagement.repository.PercentDiscountVoucherRepository;
import org.prgms.vouchermanagement.voucher.FixedAmountVoucher;
import org.prgms.vouchermanagement.voucher.PercentDiscountVoucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private final FixedAmountVoucherRepository fixedAmountVoucherRepository;
    private final PercentDiscountVoucherRepository percentDiscountVoucherRepository;

    public VoucherService(FixedAmountVoucherRepository fixedAmountVoucherRepository, PercentDiscountVoucherRepository percentDiscountVoucherRepository) {
        this.fixedAmountVoucherRepository = fixedAmountVoucherRepository;
        this.percentDiscountVoucherRepository = percentDiscountVoucherRepository;
    }

    public void createNewVoucher(VoucherType voucherType, long amountOrPercent) {
        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amountOrPercent);
        }

        else if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), amountOrPercent);
        }
    }
}
