package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> VOUCHERS = new ConcurrentHashMap<>();

    @Override
    public Voucher findVoucher(UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(VOUCHERS.get(voucherId));

        return voucher.orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public UUID saveVoucher(Voucher voucher) {
        UUID voucherId = voucher.getId();
        VOUCHERS.put(voucherId, voucher);
        return voucherId;
    }

    @Override
    public Collection<Voucher> getAllVouchers() {
        return Collections.unmodifiableCollection(VOUCHERS.values());
    }

}
