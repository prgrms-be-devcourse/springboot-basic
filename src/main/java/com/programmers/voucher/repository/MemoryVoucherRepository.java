package com.programmers.voucher.repository;

import com.programmers.voucher.domain.DiscountType;
import com.programmers.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final String NOT_FOUND_ERROR_MESSAGE = "[ERROR] 해당 요청에 대한 결과를 찾을 수 없습니다.";
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public Page<Map<String, Object>> findAllByPage(Pageable pageable) {
        return null;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        if (!storage.containsKey(voucherId)) throw new NoSuchElementException(NOT_FOUND_ERROR_MESSAGE);
        return storage.get(voucherId);
    }

    @Override
    public List<Voucher> findByType(String command) {
        return storage.values().stream()
                .filter(voucher -> voucher.getDiscount().getDiscountType().equals(DiscountType.of(command)))
                .toList();
    }

    @Override
    public void deleteById(UUID voucherId) {
        if (storage.containsKey(voucherId)) storage.remove(voucherId);
    }
}
