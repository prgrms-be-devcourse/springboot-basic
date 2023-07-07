package org.prgrms.vouchermission;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<Long, Voucher> storage = new ConcurrentHashMap<>();
    public static final AtomicLong voucherIdFactory = new AtomicLong(1);

    @Override
    public Voucher insert(Voucher voucher) {
        voucher.setRealVoucherId(voucherIdFactory.getAndIncrement());
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
                .stream()
                .toList();
    }
}
