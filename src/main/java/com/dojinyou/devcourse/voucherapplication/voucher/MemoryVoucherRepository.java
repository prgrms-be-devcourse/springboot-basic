package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherList;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    public static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private static final AtomicLong idGenerator = new AtomicLong();
    private static final Map<Long, VoucherEntity> store = new ConcurrentHashMap<>();

    @Override
    public Voucher create(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        long id = idGenerator.incrementAndGet();
        VoucherEntity voucherEntity = VoucherMapper.domainToEntity(id, voucher);
        return save(voucherEntity);
    }

    @Override
    public VoucherList findAll() {
        return null;
    }

    private Voucher save(VoucherEntity voucherEntity) {
        store.put(voucherEntity.getId(), voucherEntity);
        return VoucherMapper.entityToDomain(voucherEntity);
    }
}
