package org.prgrms.kdt.devcourse.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private static final Logger  voucherServiceLogger = LoggerFactory.getLogger(VoucherService.class);
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()-> {
                    voucherServiceLogger.info("Can't find voucher(voucherId : {})", voucherId);
                    return new RuntimeException("Can not find a voucher %s".formatted(voucherId));
                });
    }

    public void useVoucher(Voucher voucher) {
        //voucher 삭제
    }

    public Voucher createVoucher(VoucherType voucherType, long amount){

        if(voucherType == VoucherType.FIXED){
            return  voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(),amount));
        }
        else if(voucherType == VoucherType.PERCENT){
            return  voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(),amount));
        }else{
            voucherServiceLogger.info("createVoucher(VoucherType : {}, amount:{} )",voucherType,amount);
            throw new RuntimeException("voucher type error");
        }

    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

}
