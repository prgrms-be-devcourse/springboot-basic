package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Profile({"dev"})
@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private static final Map<Long, Voucher> voucherStore = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException();
        }

        if (voucher.getId() == null) {
            Voucher newVoucher = createVoucherEntity(voucher);
            voucherStore.put(newVoucher.getId(), newVoucher);
            return newVoucher;
        }

        voucherStore.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherStore.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        return Optional.ofNullable(voucherStore.get(id));
    }

    private Voucher createVoucherEntity(Voucher voucher) {
        Long id = KeyGenerator.keyGenerate();
        return Voucher.toEntity(id, voucher);
    }

}
