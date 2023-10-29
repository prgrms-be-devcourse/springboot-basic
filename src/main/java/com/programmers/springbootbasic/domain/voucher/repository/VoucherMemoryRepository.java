package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherType;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
@Profile("dev")
@NoArgsConstructor
public class VoucherMemoryRepository implements VoucherRepository {
    private Map<UUID, Voucher> voucherDB = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherDB.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherDB.values()
                .stream()
                .toList();
    }

    @Override
    public void deleteAll() {
        voucherDB = new HashMap<>();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(voucherDB.get(voucherId));
    }

    @Override
    public List<Voucher> findByVoucherType(int voucherType) {
        return voucherDB.values()
                .stream()
                .filter(voucher -> VoucherType.predictVoucherType(voucher) == voucherType)
                .toList();
    }

    @Override
    public List<Voucher> findByDate(LocalDate date) {
        return voucherDB.values()
                .stream()
                .filter(voucher -> voucher.getCreatedAt().isEqual(date))
                .toList();
    }

    @Override
    public void update(Voucher voucher) {
        voucherDB.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void delete(Voucher voucher) {
        voucherDB.remove(voucher.getVoucherId());
    }

}
