package org.programmers.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRespository implements VoucherRepository {

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
    public List<Voucher> getAllVouchers() {
        List<Voucher> voucherList = new ArrayList<>();

        storage.forEach((key, value) -> voucherList.add(value));

        return voucherList;
    }

}
