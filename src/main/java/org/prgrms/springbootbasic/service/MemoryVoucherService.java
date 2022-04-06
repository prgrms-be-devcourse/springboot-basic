package org.prgrms.springbootbasic.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.repository.VoucherRepository;

public class MemoryVoucherService {

    private final VoucherRepository voucherRepository;

    public MemoryVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(long amount) {
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), amount));
    }

    public void createPercentAmountVoucher(int percent) {
        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), percent));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
