package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    public Voucher findById(UUID voucherId) {
        Voucher voucher = voucherMap.get(voucherId);

        if (voucher == null) {
            throw new NoSuchElementException("찾는 바우처가 없습니다.");
        }

        return voucher;
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
