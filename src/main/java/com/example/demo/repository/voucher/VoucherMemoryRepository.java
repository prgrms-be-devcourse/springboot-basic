package com.example.demo.repository.voucher;

import com.example.demo.domain.voucher.Voucher;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherHashMap = new LinkedHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherHashMap.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        Voucher voucher = voucherHashMap.get(id);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(voucherHashMap.values());
    }

    @Override
    public void updateAmount(UUID id, int discountAmount) {
        Voucher voucher = voucherHashMap.get(id);
        voucher.updateDiscountAmount(discountAmount);
    }

    @Override
    public void deleteById(UUID id) {
        voucherHashMap.remove(id);
    }

    @Override
    public boolean notExistById(UUID id) {
        return !voucherHashMap.containsKey(id);
    }
}
