package org.prgrms.kdtspringw1d1.voucher;

import org.prgrms.kdtspringw1d1.VoucherType;

import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }

    public Voucher createVoucher(VoucherType type){
        Voucher createdVoucher = null;
        if(type == VoucherType.Fixed){
            createdVoucher = voucherRepository.createFixedAmountVoucher()
                    .orElseThrow(()-> new RuntimeException("Can not create voucher"));
        }
        else if(type == VoucherType.Percent){
            createdVoucher = voucherRepository.createPercentDiscountVoucher()
                    .orElseThrow(()-> new RuntimeException("Can not create voucher"));
        }
        return createdVoucher;
    }

    public List<Voucher> getVoucherAll(){
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {
        //not yet used;
    }
}
