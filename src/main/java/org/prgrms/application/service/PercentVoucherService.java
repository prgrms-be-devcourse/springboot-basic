package org.prgrms.application.service;

import org.prgrms.application.domain.voucher.FixedAmountVoucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import java.util.Random;

import static java.lang.Math.abs;

@Service
public class PercentVoucherService extends VoucherService{

    public PercentVoucherService(VoucherRepository voucherRepository) {
        super(voucherRepository);
    }

    @Override
    public void createVoucher(VoucherType voucherType, double discountAmount) {
        long randomId = abs(new Random().nextLong());
        System.out.println(discountAmount);
        FixedAmountVoucher voucher = new FixedAmountVoucher(randomId, voucherType, discountAmount);
        System.out.println(discountAmount);
        voucherRepository.insert(toEntity(voucher));
    }

}
