package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("file") final VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public static Voucher createVoucher(final String voucherType, final long discountValue) {
        System.out.println(MessageFormat.format("{0}가 생성되었습니다.", voucherType));
        if (voucherType.equals("FixedAmountVoucher")) {
            return new FixedAmountVoucher(UUID.randomUUID(), discountValue);
        } else if (voucherType.equals("PercentDiscountVoucher")) {
            return new PercentDiscountVoucher(UUID.randomUUID(), discountValue);
        } else {
            System.out.println("Voucher Type 입력값이 잘못되었습니다.");
            return null;
        }
    }

    public Voucher getVoucher(final UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(final Voucher voucher) {

    }
}
