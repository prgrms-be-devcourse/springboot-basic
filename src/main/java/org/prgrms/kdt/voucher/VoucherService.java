package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Cannot find a voucher{0}", voucherId)));
    }

    public Voucher createVoucher(UUID voucherId, VoucherType voucherType, long discount, LocalDateTime createdAt){
        Voucher voucher = null;
        switch (voucherType){
            case FIXED:
                voucher = new FixedAmountVoucher(voucherId, discount, createdAt);
                break;
            case PERCENT:
                voucher = new PercentDiscountVoucher(voucherId, discount, createdAt);
                break;
        }
        return voucherRepository.insert(voucher);

    }

    public void useVoucher(Voucher voucher){

    }

}
