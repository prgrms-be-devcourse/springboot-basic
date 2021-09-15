package org.prgrms.kdtspringhw.voucher.repository;


import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId)); //null인경우 empty반환
    }
    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }
    @Override
    public  Map<UUID,Voucher> returnAll() {
        return storage;
    }

}
