package kr.co.springbootweeklymission.domain.voucher.dao;

import kr.co.springbootweeklymission.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> VOUCHER_MEMORY = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        return VOUCHER_MEMORY.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(VOUCHER_MEMORY.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return VOUCHER_MEMORY.values()
                .stream()
                .toList();
    }
}
