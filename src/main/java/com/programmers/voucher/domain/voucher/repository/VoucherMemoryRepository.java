package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.voucher.global.util.DataAccessConstants.UPDATE_ONE;

@Repository
@Profile("memory")
public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> store = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return store.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher voucher = store.get(voucherId);
            return Optional.ofNullable(voucher);
        } catch (NullPointerException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll(VoucherType voucherType, LocalDateTime startTime, LocalDateTime endTime) {
        throw new UnsupportedOperationException("Voucher list search is not supported");
    }

    @Override
    public void deleteById(UUID voucherId) {
        Voucher remove = store.remove(voucherId);
        if(remove == null) {
            throw new IncorrectResultSizeDataAccessException(UPDATE_ONE, 0);
        }
    }
}
