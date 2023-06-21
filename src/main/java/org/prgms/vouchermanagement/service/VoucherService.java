package org.prgms.vouchermanagement.service;

import org.prgms.vouchermanagement.repository.FixedAmountVoucherRepository;
import org.prgms.vouchermanagement.repository.PercentDiscountVoucherRepository;
import org.prgms.vouchermanagement.voucher.FixedAmountVoucher;
import org.prgms.vouchermanagement.voucher.PercentDiscountVoucher;
import org.prgms.vouchermanagement.voucher.Voucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
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
            fixedAmountVoucherRepository.saveVoucher(voucher.getVoucherId(), voucher);

        } else if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), amountOrPercent);
            percentDiscountVoucherRepository.saveVoucher(voucher.getVoucherId(), voucher);
        }
    }

    public Optional<Map<UUID, Voucher>> getVoucherList(VoucherType voucherType) {
        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            return fixedAmountVoucherRepository.getVoucherList();

        } else if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            return percentDiscountVoucherRepository.getVoucherList();
        }

        return Optional.empty();
    }
}
