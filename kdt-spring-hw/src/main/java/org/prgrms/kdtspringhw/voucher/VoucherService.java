package org.prgrms.kdtspringhw.voucher;

import org.prgrms.kdtspringhw.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringhw.voucher.voucherObj.FixedAmountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.PercentDiscountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository  ;

    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }
    // enum으로 -> 별도로 사용하지 않으면 VOID fh qkRnwk(insert)
    public void createVoucher(String type){
        Voucher voucher ;
        if(type.equals("fix")){
            voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        }
        else if(type.equals("per")){
            voucher = voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 10));
        }
        else {
            //예외처리 -> swich : default
        }
    }

    public Map<UUID,Voucher> getVouchers() {return voucherRepository.returnAll();};

}
