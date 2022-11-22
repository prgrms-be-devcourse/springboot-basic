package org.prgrms.springorder.domain.voucher.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.putIfAbsent(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
            .stream()
            .toList();
    }

    @Override
    public void deleteAll() {
        this.storage.clear();
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.remove(voucher.getVoucherId());
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<CustomerWithVoucher> findByIdWithCustomer(UUID voucherId) {
        throw new RuntimeException("지원되지 않는 기능입니다.");
    }

    @Override
    public void deleteById(UUID voucherId) {
        this.storage.remove(voucherId);
    }

    @Deprecated
    @Override
    public List<Voucher> findAllBy(LocalDateTime startDate, LocalDateTime endDate,
        VoucherType voucherType) {
        throw new RuntimeException("지원되지 않는 기능입니다.");
    }

}