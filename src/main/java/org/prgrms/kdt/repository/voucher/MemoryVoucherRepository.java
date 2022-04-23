package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Qualifier("memory")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void delete(UUID voucherId) {

    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
