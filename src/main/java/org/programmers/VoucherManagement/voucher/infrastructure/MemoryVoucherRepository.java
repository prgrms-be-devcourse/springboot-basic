package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> map = new LinkedHashMap<>();

    @Override
    public void save(Voucher voucher) {
        map.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void update(Voucher voucher) {
        if (map.containsKey(voucher.getVoucherId())) {
            map.put(voucher.getVoucherId(), voucher);
        }
    }

    @Override
    public void delete(Voucher voucher) {
        map.remove(voucher.getVoucherId());
    }

    @Override
    public void deleteAll() {
        map.clear();
    }

    @Override
    public List<Voucher> findAll() {
        return map.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(map.get(voucherId));
    }
}
