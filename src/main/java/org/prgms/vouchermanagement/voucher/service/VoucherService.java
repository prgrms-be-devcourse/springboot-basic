package org.prgms.vouchermanagement.voucher.service;

import org.prgms.vouchermanagement.voucher.domain.entity.FixedAmountVoucher;
import org.prgms.vouchermanagement.voucher.domain.entity.PercentDiscountVoucher;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("jdbc") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createNewVoucher(VoucherType voucherType, long amountOrPercent) {
        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amountOrPercent);
            voucherRepository.saveVoucher(voucher);
        }

        if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), amountOrPercent);
            voucherRepository.saveVoucher(voucher);
        }
    }

    public Map<UUID, Voucher> getVoucherList() {
        return voucherRepository.getVoucherList();
    }
}
