package org.prgms.repository;

import org.prgms.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
//@Profile({"local", "default"})
public class MemoryVoucherRepository implements VoucherRepository {
    private final List<Voucher> db = new ArrayList<>();

    public Voucher save(Voucher voucher) {
        db.add(voucher);
        return voucher;
    }

    public List<Voucher> findAll() {
        return db;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return db.stream().filter(voucher -> voucher.getVoucherId() == voucherId).findFirst();
    }

    @Override
    public void deleteAll() {
        while (!db.isEmpty()) {
            db.remove(0);
        }
    }

    @Override
    public int deleteById(UUID voucherId) {
        Optional<Voucher> voucher = db.stream().filter(v -> v.getVoucherId() == voucherId).findFirst();

        if (voucher.isEmpty()) {
            return 0;
        }

        db.remove(voucher.get());
        return 1;
    }
}
