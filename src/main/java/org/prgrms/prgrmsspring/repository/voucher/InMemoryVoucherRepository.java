package org.prgrms.prgrmsspring.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Profile("local")
@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public Voucher update(Voucher voucher) {
        findById(voucher.getVoucherId()).ifPresentOrElse(
                existingVoucher -> {
                    delete(existingVoucher.getVoucherId());
                    insert(voucher);
                },
                () -> {
                    throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage());
                }
        );
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        store.remove(voucherId);
    }
}