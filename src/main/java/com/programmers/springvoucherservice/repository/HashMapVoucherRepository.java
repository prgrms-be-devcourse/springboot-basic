package com.programmers.springvoucherservice.repository;

import com.programmers.springvoucherservice.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("test")
@Repository
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
    public UUID registerVoucher(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        map.put(voucherId, voucher);

        return voucherId;
    }
}
