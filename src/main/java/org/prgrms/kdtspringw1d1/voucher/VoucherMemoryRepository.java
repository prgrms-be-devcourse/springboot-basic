package org.prgrms.kdtspringw1d1.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherMemoryRepository implements VoucherRepository {

    // 메모리 저장소로 ArrayList 사용
    private List<Voucher> vouchers = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        // ArrayList에서 stream 객체를 활용해 일치하는 객체 반환
        return Optional.ofNullable(vouchers.stream().filter(v -> v.getVoucherId().equals(voucherId)).findAny().orElse(null));
    }

    @Override
    public Voucher createFixedAmountVoucher() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        vouchers.add(fixedAmountVoucher);
        return fixedAmountVoucher;
    }

    @Override
    public Voucher createPercentDiscountVoucher() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        vouchers.add(percentDiscountVoucher);
        return percentDiscountVoucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers;
    }
}
