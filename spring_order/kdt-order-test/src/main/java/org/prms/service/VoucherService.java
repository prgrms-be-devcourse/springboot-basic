package org.prms.service;

import org.prms.domain.FixedAmountVoucher;
import org.prms.domain.PercentDiscountVoucher;
import org.prms.domain.Voucher;
import org.prms.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Primary
public class VoucherService {

    private final VoucherRepository voucherRepository;
//    @Autowired
    public VoucherService(@Qualifier("fileRepo") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherID) {
        // voucherID를 찾지 못하는 경우에는 Throw로 날려서 Exception 처리
        return voucherRepository
                .findById(voucherID)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Can not find a Voucher for {0}",voucherID)));
    }

    public void createVoucher(String voucherType,Long val) {

        var voucherId=UUID.randomUUID();

        if (voucherType.equals("fixed")) {
            voucherRepository.insert(new FixedAmountVoucher(voucherId,val));
        }
        else {
//            voucherRepository.insert(uuid,new PercentDiscountVoucher(uuid,val));
            voucherRepository.insert(new PercentDiscountVoucher(voucherId,val));
        }
    }

    public ConcurrentHashMap<UUID,Voucher> getVoucherList() {
        return voucherRepository.getList();
    }



    //Todo
    public void useVoucher(Voucher voucher) {

    }


}
