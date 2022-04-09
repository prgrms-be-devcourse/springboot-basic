package org.prgrms.kdt.domain.voucher.service;

import org.prgrms.kdt.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void save(VoucherType voucherType, long discount) {
        UUID voucherId = UUID.randomUUID();
        if(voucherType == VoucherType.FIXED_AMOUNT){
            Voucher voucher = new FixedAmountVoucher(voucherId, discount);
            voucherRepository.save(voucher);
        } else if(voucherType == VoucherType.PERCENT_DISCOUNT){
            Voucher voucher = new PercentDiscountVoucher(voucherId, (int) discount);
            voucherRepository.save(voucher);
        }
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }


}
