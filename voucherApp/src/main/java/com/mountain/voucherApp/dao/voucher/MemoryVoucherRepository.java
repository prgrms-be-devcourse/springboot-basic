package com.mountain.voucherApp.dao.voucher;

import com.mountain.voucherApp.model.VoucherEntity;
import com.mountain.voucherApp.model.enums.DiscountPolicy;
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
public class MemoryVoucherRepository implements VoucherRepository {

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
    public Optional<VoucherEntity> findByDiscountPolicyAndAmount(DiscountPolicy discountPolicy, long discountAmount) {
        return storage.values().stream()
                .filter((e) -> (e.getDiscountPolicy() == discountPolicy) && (e.getDiscountAmount() == discountAmount))
                .findFirst();
    }

    @Override
    public void deleteById(UUID voucherId) {
    }

    public void clear() {
        storage.clear();
    }
}
