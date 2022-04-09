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

    public UUID save(VoucherType voucherType, long discount) {
        UUID uuid = UUID.randomUUID();
        if(voucherType == VoucherType.FIXED_AMOUNT){
            Voucher voucher = new FixedAmountVoucher(uuid, discount);
            voucherRepository.save(voucher);
        } else if(voucherType == VoucherType.PERCENT_DISCOUNT){
            Voucher voucher = new PercentDiscountVoucher(uuid, (int) discount);
            voucherRepository.save(voucher);
        }
        return uuid;
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }


}
