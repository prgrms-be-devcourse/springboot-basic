package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher update(Voucher voucher) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(UUID voucherId) {
        throw new UnsupportedOperationException();
    }

}
