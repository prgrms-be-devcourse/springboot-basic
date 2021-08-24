package org.prgrms.kdtspringw1d1.voucher;

import org.prgrms.kdtspringw1d1.VoucherType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }

    public Voucher createVoucher(VoucherType type){
        if(type == VoucherType.FIXED){
            return voucherRepository.createFixedAmountVoucher();
        }
        else{
            return voucherRepository.createPercentDiscountVoucher();
        }
    }

    public List<Voucher> getVoucherAll(){
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {
        //not yet used;
    }
}
