package org.programmer.kdtspringboot.voucher;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for {0}"+ voucherId));
    }

    public Voucher createFixedAmountVoucher(UUID voucherId,long amount ){
        Voucher voucher = new FixedAmountVoucher(voucherId,amount);
        return voucherRepository.insert(voucher);
    }

    public Voucher createPercentDiscountVoucher(UUID voucherId,long percent){
        Voucher voucher = new PercentDiscountVoucher(voucherId,percent);
        return voucherRepository.insert(voucher);
    }

    public Map<UUID,Voucher> findAllVouchers(){
        return voucherRepository.findAll();
    }
}
