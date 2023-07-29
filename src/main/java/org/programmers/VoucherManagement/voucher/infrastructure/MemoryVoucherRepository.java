package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<String, Voucher> map = new LinkedHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        map.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void update(Voucher voucher) {
        if (map.containsKey(voucher.getVoucherId())) {
            map.put(voucher.getVoucherId(), voucher);
        }
    }

    @Override
    public void delete(String voucherId) {
        map.remove(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        return Optional.ofNullable(map.get(voucherId));
    }
}
