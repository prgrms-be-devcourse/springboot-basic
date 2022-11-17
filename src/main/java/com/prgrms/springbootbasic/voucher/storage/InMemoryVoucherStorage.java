package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.voucher.domain.Voucher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("develop")
@Repository
public class InMemoryVoucherStorage implements VoucherStorage {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getUUID(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    public Voucher findById(UUID uuid){
        return vouchers.get(uuid);
    }
}
