package org.prgrms.kdt;

import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(MemoryVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }


    public void createVoucher(String type){
        Voucher voucher ;
        if(type.equals("fix")){
            voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        }
        else if(type.equals("per")){
            voucher = voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 10));
        }
    }

    public Map<UUID,Voucher> getVouchers() {return voucherRepository.returnAll();};

}
