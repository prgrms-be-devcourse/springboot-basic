package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> vouchers = new LinkedHashMap<>();

    @Override
    public Optional<Voucher> findVoucher(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public void saveVoucher(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

}
