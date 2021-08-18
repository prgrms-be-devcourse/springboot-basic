package org.prgrms.kdtspringw1d1.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherMemoryRepository implements VoucherRepository{

    // 현재
    private ArrayList<Voucher> vouchers = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> createFixedAmountVoucher() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),100);
        vouchers.add(fixedAmountVoucher);
        return Optional.of(fixedAmountVoucher);
    }

    @Override
    public Optional<Voucher> createPercentDiscountVoucher() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
        vouchers.add(percentDiscountVoucher);
        return Optional.of(percentDiscountVoucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers;
    }
}
