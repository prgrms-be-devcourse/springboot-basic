package org.prgms.order.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class VoucherService {

    //이렇게 필드에다가 autowired를 줄 수도 있고
    @Autowired //Autowired는 final 사용 불가
    private VoucherRepository voucherRepository;

//    public VoucherService(VoucherRepository voucherRepository) {
//        this.voucherRepository = voucherRepository;
//    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format("Can not find a voucher for {0}", voucherId)
                ));
    }

    public void useVoucher(Voucher voucher) {
    }
}
