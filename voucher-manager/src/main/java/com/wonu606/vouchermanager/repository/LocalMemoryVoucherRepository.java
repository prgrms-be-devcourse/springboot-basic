package com.wonu606.vouchermanager.repository;

import com.wonu606.vouchermanager.domain.Voucher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class LocalMemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherStore = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        if (voucherStore.containsKey(voucher.getUuid())) {
            throw new IllegalArgumentException(
                    "이미 존재하는 바우처의 uuid입니다. [uuid]: " + voucher.getUuid());
        }

        voucherStore.put(voucher.getUuid(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID uuid) {
        return Optional.ofNullable(voucherStore.get(uuid));
    }

    @Override
    public List<Voucher> findAll() {
        ArrayList<Voucher> voucherList = new ArrayList<>(voucherStore.values());
        return Collections.unmodifiableList(voucherList);
    }

    @Override
    public void deleteById(UUID uuid) {
        voucherStore.remove(uuid);
    }

    @Override
    public void deleteAll() {
        voucherStore.clear();
    }
}
