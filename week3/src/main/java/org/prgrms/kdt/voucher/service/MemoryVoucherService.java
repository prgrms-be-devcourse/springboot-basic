package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemoryVoucherService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public MemoryVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void create(Voucher voucher)  {
        voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> list()  {
         return voucherRepository.findAll();
    }

    @Override
    public void create(String voucherTypeStr, Long amount) {
        var voucherType = VoucherType.valueOf(voucherTypeStr);
        switch (voucherType){
            case PERCENT_DISCOUNT_VOUCHER -> voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), amount, voucherType, LocalDateTime.now()));
            case FIXED_AMOUNT_VOUCHER -> voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), amount, voucherType, LocalDateTime.now()));
        };
    }

    @Override
    public Optional<Voucher> getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }
}