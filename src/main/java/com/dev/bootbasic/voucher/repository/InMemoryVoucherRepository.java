package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findVoucher(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public UUID saveVoucher(Voucher voucher) {
        UUID voucherId = voucher.getId();
        vouchers.put(voucherId, voucher);
        return voucherId;
    }

}
