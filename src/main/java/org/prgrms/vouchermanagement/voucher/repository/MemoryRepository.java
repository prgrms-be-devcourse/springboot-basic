package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;
import org.prgrms.vouchermanagement.voucher.policy.PolicyStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public int create(UUID voucherId, DiscountPolicy discountPolicy) {
        Voucher voucher = new Voucher(voucherId, discountPolicy);
        storage.put(voucherId, voucher);

        return 0;
    }

    @Override
    public List<Voucher> voucherLists() {
        return storage.values().stream()
                .toList();
    }

    @Override
    public void update(UUID voucherId, long amount) {
    }

    @Override
    public int delete(UUID voucherId) {

        return 0;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return storage.get(voucherId);
    }

    @Override
    public List<Voucher> findVoucherByPolicy(PolicyStatus policy) {
        return null;
    }
}
