package org.prgrms.voucherapp.engine;

import org.prgrms.voucherapp.global.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public ArrayList<Voucher> getVoucherAll() {
        var voucherList = new ArrayList<Voucher>();
        storage.forEach((uuid, voucher) -> {
            voucherList.add(voucher);
        });
        return voucherList;
    }
}
