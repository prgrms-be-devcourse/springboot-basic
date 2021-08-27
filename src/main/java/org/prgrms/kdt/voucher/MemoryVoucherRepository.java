package org.prgrms.kdt.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> memoryVoucher = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        memoryVoucher.put(voucher.getID(), voucher);
        return voucher;
    }

    @Override
    public int numVouchers() {
        return memoryVoucher.size();
    }

    @Override
    public List<Voucher> getList() {
        return memoryVoucher.entrySet().stream().map((o) -> o.getValue()).toList();
    }

}
