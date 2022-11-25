package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Profile("test")
@Component
public class HashMapVoucherRepository implements VoucherRepository {
    Map<UUID, Voucher> map = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return new LinkedList<>(map.values());
    }

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        map.put(voucherId, voucher);

        return voucher;
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        map.remove(voucherId);
    }

    @Override
    public List<Voucher> findByType(String name) {
        List<Voucher> vouchers = new ArrayList<>();
        for (UUID id : map.keySet()) {
            Voucher voucher = map.get(id);

            if (voucher.getType().toString().equals(name)) {
                vouchers.add(voucher);
            }
        }

        return vouchers;
    }

    @Override
    public List<Voucher> findByPeriod(LocalDateTime from, LocalDateTime to) {
        return null;
    }
}
