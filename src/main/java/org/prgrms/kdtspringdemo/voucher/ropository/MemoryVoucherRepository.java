package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.exception.VoucherIdNotFoundException;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher == null) {
            logger.error("원인 : {} -> 에러 메시지 : {}", voucherId, NOT_FOUND_VOUCHER);
            throw new VoucherIdNotFoundException(NOT_FOUND_VOUCHER);
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public Voucher update(Voucher voucher) {
        Voucher updatedVoucher = storage.putIfAbsent(voucher.getVoucherId(), voucher);

        return updatedVoucher == null ? voucher : updatedVoucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }
}
