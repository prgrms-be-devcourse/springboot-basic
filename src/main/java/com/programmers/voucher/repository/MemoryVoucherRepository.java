package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final String NOT_FOUND_ERROR_MESSAGE = "[ERROR] 해당 요청에 대한 결과를 찾을 수 없습니다.";
    private static final Map<UUID, Voucher> STORAGE = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        STORAGE.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return STORAGE.values().stream().toList();
    }

    @Override
    public Voucher findById(UUID voucherId) {
        if (!STORAGE.containsKey(voucherId)) throw new NoSuchElementException(NOT_FOUND_ERROR_MESSAGE);
        return STORAGE.get(voucherId);

    }
}
