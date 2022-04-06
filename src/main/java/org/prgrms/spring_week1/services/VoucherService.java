package org.prgrms.spring_week1.services;

import org.prgrms.spring_week1.models.FixedAmountVoucher;
import org.prgrms.spring_week1.models.PercentDiscountVoucher;
import org.prgrms.spring_week1.models.Voucher;
import org.prgrms.spring_week1.models.VoucherStatus;
import org.prgrms.spring_week1.repositories.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository.findById(voucherId);
    }

    public Voucher createFixedVoucher(long amount){
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher createPercentVoucher(long percent){
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.insert(voucher);
        return voucher;
    }


}
