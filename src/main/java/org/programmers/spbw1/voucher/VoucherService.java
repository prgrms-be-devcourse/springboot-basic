package org.programmers.spbw1.voucher;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID id){
        return voucherRepository
                .getVoucherById(id)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("No such Voucher, id :  {0}", id)));
    }

    public long useVoucher(Voucher voucher, Long amount){
        return voucher.discount(amount);
    }

    public Voucher createVoucher(VoucherType voucherType, Long discountAmount){
        if(voucherType == VoucherType.FIXED)
            return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), discountAmount));
        return voucherRepository.insert(new PercentVoucher(UUID.randomUUID(), discountAmount));
    }
}
