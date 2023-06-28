package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.entity.VoucherEntity;
import com.programmers.voucher.domain.VoucherMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final String NOT_FOUND_ERROR_MESSAGE = "[ERROR] 해당 요청에 대한 결과를 찾을 수 없습니다.";
    private static final Map<UUID, VoucherEntity> STORAGE = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        VoucherEntity voucherEntity = VoucherMapper.convertDomainToEntity(voucher);
        STORAGE.put(voucherEntity.getVoucherId(), voucherEntity);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return STORAGE.values().stream().map(VoucherMapper::convertEntityToDomain).toList();
    }

    @Override
    public Voucher findById(UUID voucherId) {
        if (!STORAGE.containsKey(voucherId)) throw new NoSuchElementException(NOT_FOUND_ERROR_MESSAGE);
        return VoucherMapper.convertEntityToDomain(STORAGE.get(voucherId));

    }
}
