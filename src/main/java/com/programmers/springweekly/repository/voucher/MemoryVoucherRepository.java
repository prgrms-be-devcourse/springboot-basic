package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public void update(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = voucherMap.get(voucherId);

        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public void deleteById(UUID voucherId) {
        voucherMap.remove(voucherId);
    }

    @Override
    public void deleteAll() {
        voucherMap.clear();
    }
}
