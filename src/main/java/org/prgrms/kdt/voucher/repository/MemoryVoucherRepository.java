package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemoryVoucherRepository implements VoucherRepository {
    public static Voucher create(final String voucherType, final long discountValue) {
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

    @Override
    public Optional<Voucher> findById(final UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
