package org.prgrms.kdtspringdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemoryVoucherRepository implements VoucherRepository {
    private List<Voucher> vouchers = new ArrayList<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        this.vouchers.add(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers;
    }
}
