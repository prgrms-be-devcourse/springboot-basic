package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    @Override
    public Voucher insert(UUID voucherID, Voucher voucher){
        storage.put(voucherID,voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherID){
        return Optional.ofNullable(storage.get(voucherID));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

}
