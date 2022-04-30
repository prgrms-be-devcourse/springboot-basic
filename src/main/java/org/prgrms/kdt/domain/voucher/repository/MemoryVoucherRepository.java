package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.exception.VoucherDataException;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdt.domain.common.exception.ExceptionType.NOT_SUPPORTED;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public UUID save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher.getVoucherId();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(storage.values().stream().toList());
    }

    @Override
    public List<Voucher> findByCustomerId(UUID voucherId) {
        throw new VoucherDataException(NOT_SUPPORTED);
    }

    @Override
    public int update(Voucher voucher) {
        throw new VoucherDataException(NOT_SUPPORTED);
    }

    @Override
    public int updateCustomerId(UUID voucherId, UUID customerId) {
        throw new VoucherDataException(NOT_SUPPORTED);
    }

    @Override
    public int deleteById(UUID voucherId) {
        throw new VoucherDataException(NOT_SUPPORTED);
    }

    @Override
    public int deleteAll() {
        throw new VoucherDataException(NOT_SUPPORTED);
    }

    @Override
    public int deleteByCustomerId(UUID customerId) {
        throw new VoucherDataException(NOT_SUPPORTED);
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType voucherType, LocalDate date) {
        throw new VoucherDataException(NOT_SUPPORTED);
    }
}
