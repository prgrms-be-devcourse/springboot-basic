package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> vouchers;
    public MemoryVoucherRepository() {
        this.vouchers = new ConcurrentHashMap<>();
    }
    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }
    @Override
    public List<Voucher> findAllVouchers() {
        return vouchers.values().stream().toList();
    }
}
