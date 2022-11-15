package org.prgrms.voucherapplication.voucher.repository;

import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class VoucherMemoryRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getUuid(), voucher);
    }

    @Override
    public String findAll() {
        final StringBuilder stringBuilder = new StringBuilder();
        storage.forEach(
                (uuid, voucher) -> stringBuilder.append(voucher.toString()));
        return stringBuilder.toString();
    }
}
