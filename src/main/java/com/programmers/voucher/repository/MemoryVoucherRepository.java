package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherEntity;
import com.programmers.voucher.domain.VoucherMapper;
import com.programmers.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, VoucherEntity> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        VoucherEntity voucherEntity = VoucherMapper.domainToEntity(voucher);
        storage.put(voucherEntity.getVoucherId(), voucherEntity);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        if (storage.isEmpty()) {
            return Collections.emptyList();
        }
        return storage.values().stream().map(VoucherMapper::entityToDomain).toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException("아직 개발 중입니다.");
    }
}
