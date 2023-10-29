package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers;

    public MemoryVoucherRepository() {
        this.vouchers = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public void deleteById(UUID voucherId) {
        vouchers.remove(voucherId);
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
    }

    @Override
    public void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        Voucher voucher = vouchers.get(voucherId);
        voucher.updateVoucherInfo(voucherUpdateRequest);
    }
}
