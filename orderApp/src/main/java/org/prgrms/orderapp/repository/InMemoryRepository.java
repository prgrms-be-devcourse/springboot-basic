package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.Voucher;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryRepository implements VoucherRepository {
    private List<Voucher> vouchers;

    public InMemoryRepository() {
        this.vouchers = new ArrayList<>();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        for(Voucher voucher:vouchers) {
            if (voucher.getVoucherId().equals(voucherId))
                return Optional.of(voucher);
        }
        return Optional.empty();
    }

    @Override
    public void save(Voucher voucher) {
        vouchers.add(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers;
    }
}
