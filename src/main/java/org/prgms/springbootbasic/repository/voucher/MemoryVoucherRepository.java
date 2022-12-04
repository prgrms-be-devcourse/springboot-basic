package org.prgms.springbootbasic.repository.voucher;

import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> memoryCache = new HashMap<>();
    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(memoryCache.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        memoryCache.putIfAbsent(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public UUID deleteById(UUID voucherId) {
        return memoryCache.remove(voucherId).getVoucherId();
    }


    @Override
    public UUID deleteByCustomerId(UUID customerId) {
        for(Map.Entry<UUID, Voucher> set : memoryCache.entrySet()) {
            if(set.getValue().getCustomerId().equals(customerId)) {
                memoryCache.remove(set.getKey());
            }
        }
        return customerId;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        for (Map.Entry<UUID, Voucher> set : memoryCache.entrySet()) {
            if (set.getValue().getVoucherId().equals(voucherId)) {
                return Optional.of(set.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        List<Voucher> result = new ArrayList<>();
        for (Map.Entry<UUID, Voucher> set : memoryCache.entrySet()) {
            if (set.getValue().getCustomerId().equals(customerId)) {
                result.add(set.getValue());
            }
        }
        return result;
    }

    @Override
    public Voucher updateByCustomerId(Voucher voucher) {
        return voucher;
    }

    @Override
    public UUID updateByCustomerId(UUID customerId, UUID voucherID) {
        return customerId;
    }
}
