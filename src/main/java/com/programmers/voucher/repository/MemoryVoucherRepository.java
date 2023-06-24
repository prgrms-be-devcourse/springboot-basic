package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final static Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public UUID save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher.getVoucherId();
    }

    @Override
    public List<VoucherResponseDto> findAll() {
        if (storage.isEmpty()) {
            return Collections.emptyList();

        }
        return storage.values().stream().map(VoucherResponseDto::new).toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException("아직 개발 중입니다.");
    }
}
