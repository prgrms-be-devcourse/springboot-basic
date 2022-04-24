package org.prgms.voucher.repository;

import org.prgms.voucher.domain.Voucher;
import org.prgms.voucher.domain.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
//@Profile({"local", "default"})
public class MemoryVoucherRepository implements VoucherRepository {
    private final List<Voucher> db = new ArrayList<>();

    public void save(Voucher voucher) {
        db.add(voucher);
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
}
