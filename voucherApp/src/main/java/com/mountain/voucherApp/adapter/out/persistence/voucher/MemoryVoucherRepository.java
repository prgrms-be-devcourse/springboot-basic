package com.mountain.voucherApp.adapter.out.persistence.voucher;

import com.mountain.voucherApp.application.port.out.VoucherPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherPort {

    private static final Map<UUID, VoucherEntity> storage = new ConcurrentHashMap<>();

    public Optional<VoucherEntity> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<VoucherEntity> findAll() {
        return storage.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public VoucherEntity insert(VoucherEntity voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<VoucherEntity> findByPolicyIdAndDiscountAmount(int discountPolicyId, long discountAmount) {
        return storage.values().stream()
                .filter((e) -> (e.getDiscountPolicyId() == discountPolicyId) && (e.getDiscountAmount() == discountAmount))
                .findFirst();
    }

    public void clear() {
        storage.clear();
    }
}
