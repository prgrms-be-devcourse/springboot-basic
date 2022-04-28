package org.devcourse.voucher.voucher.repository;

import org.devcourse.voucher.voucher.model.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> store = new LinkedHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        store.forEach(((uuid, voucher) -> vouchers.add(voucher)));
        return vouchers;
    }

}
