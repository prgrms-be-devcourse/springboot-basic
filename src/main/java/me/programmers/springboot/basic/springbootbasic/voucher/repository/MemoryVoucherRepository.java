package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev", "test"})
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
