package org.prgms.vouchermanagement.voucher.service;

import org.prgms.vouchermanagement.voucher.repository.MemoryVoucherRepository;
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
    private final MemoryVoucherRepository memoryVoucherRepository;

    public VoucherService(MemoryVoucherRepository memoryVoucherRepository) {
        this.memoryVoucherRepository = memoryVoucherRepository;
    }

    public void createNewVoucher(VoucherType voucherType, long amountOrPercent) {
        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amountOrPercent);
            memoryVoucherRepository.saveVoucher(voucher.getVoucherId(), voucher);

        } else if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            PercentDiscountVoucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), amountOrPercent);
            memoryVoucherRepository.saveVoucher(voucher.getVoucherId(), voucher);
        }
    }

    public Optional<Map<UUID, Voucher>> getVoucherList(VoucherType voucherType) {
        return memoryVoucherRepository.getVoucherList();
    }
}
