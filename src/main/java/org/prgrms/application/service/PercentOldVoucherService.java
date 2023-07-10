package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.FixedAmountVoucher;
import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import java.util.Random;

import static java.lang.Math.abs;

@Service
public class PercentOldVoucherService extends OldVoucherService {

    public PercentOldVoucherService(VoucherRepository voucherRepository) {
        super(voucherRepository);
    }

    @Override
    public void createVoucher(double discountAmount) {
        long randomId = abs(new Random().nextLong());
        System.out.println(discountAmount);
        FixedAmountVoucher voucher = new FixedAmountVoucher(randomId,discountAmount);
        System.out.println(discountAmount);
        voucherRepository.insert(toEntity(voucher));
    }

    private VoucherEntity toEntity(Voucher voucher){
        return new VoucherEntity(voucher.getVoucherId(),VoucherType.PERCENT.name(), voucher.getDiscountAmount());
    }


}
