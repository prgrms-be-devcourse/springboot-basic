package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.aop.TrackTime;
import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;




@Repository("local")
@Profile({"local", "test"})
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    @Override
    public Optional<Voucher> findByiD(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    @TrackTime
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().
                stream().
                toList();
    }

}
