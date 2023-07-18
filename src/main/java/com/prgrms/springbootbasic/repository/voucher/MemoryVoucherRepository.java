package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findByCreatedAt() {
        return new ArrayList<>(storage.values());
    }


    @Override
    public List<Voucher> findByType(VoucherType type) {
        return storage.values().stream()
                .filter(voucher -> voucher.getVoucherType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        if (storage.containsKey(voucherId)) {
            storage.put(voucherId, voucher);
        } else {
            log.error("업테이트 시 오류가 발생했습니다.");
        }
    }

    @Override
    public int deleteById(UUID voucherId) {
        try {
            storage.remove(voucherId);
            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public boolean checkVoucherId(UUID voucherId) {
        return false;
    }
}
