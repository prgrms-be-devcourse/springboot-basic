package org.prgrms.kdtspringw1d1.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class VoucherFileRepository implements VoucherRepository{

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher createFixedAmountVoucher() {
        return null;
    }

    @Override
    public Voucher createPercentDiscountVoucher() {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
