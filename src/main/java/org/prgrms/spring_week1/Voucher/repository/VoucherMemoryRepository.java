package org.prgrms.spring_week1.Voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {

    ConcurrentHashMap<UUID, Voucher> voucherHashMap = new ConcurrentHashMap<>();


    @Override
    public Voucher insert(Voucher voucher) {
        voucherHashMap.put(voucher.getVoucherId(), voucher);
        return voucher;
    }


    @Override
    public List<Voucher> getAllVoucher() {
        List<Voucher> vouchers = new ArrayList<>();

        for (Voucher v : voucherHashMap.values()) {
            vouchers.add(v);
        }
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Voucher> findByCustomer(UUID customerId) {
        return null;
    }
}
