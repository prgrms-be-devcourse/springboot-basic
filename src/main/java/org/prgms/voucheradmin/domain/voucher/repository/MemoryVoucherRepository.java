package org.prgms.voucheradmin.domain.voucher.repository;

import org.prgms.voucheradmin.domain.voucher.console.VoucherConsole;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher create(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getAll() {
        return null;
    }
}
