package com.example.demo.domain.voucher;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherHashMap = new LinkedHashMap<>();

    @Override
    public void save(Voucher voucher) {
        voucherHashMap.put(voucher.getUUID(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        Voucher voucher = voucherHashMap.get(id);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherHashMap
                .values()
                .stream()
                .toList();
    }
}
