package org.prgrms.kdt.devcourse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()-> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }

    public void useVoucher(Voucher voucher) {
        //voucher 삭제
    }

    public Voucher createVoucher(VoucherType voucherType, long amount){

        if(voucherType == VoucherType.FIXED_AMOUNT){
            return  voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(),amount));
        }
        else if(voucherType == VoucherType.PERCENTAGE){
            return  voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(),amount));
        }else{
            throw new RuntimeException("voucher type error");
        }

    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

}
