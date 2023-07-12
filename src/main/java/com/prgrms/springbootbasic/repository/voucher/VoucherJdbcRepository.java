package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class VoucherJdbcRepository implements VoucherRepository {

    @Override
    public Voucher create(Voucher voucher) {
        return null;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return null;
    }

    @Override
    public Voucher findByCreatedAt(LocalDateTime createAt) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public void update(Voucher voucher) {

    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }
}
