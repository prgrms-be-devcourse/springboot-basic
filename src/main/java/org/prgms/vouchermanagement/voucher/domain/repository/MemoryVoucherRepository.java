package org.prgms.vouchermanagement.voucher.domain.repository;

import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository{
    private final List<Voucher> voucherStorage = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return voucherStorage.stream()
                .filter(voucher -> voucher.getVoucherId() == voucherId)
                .findAny();
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherStorage.add(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        if (!voucherStorage.isEmpty()) {
            return Collections.unmodifiableList(voucherStorage);
        }
        return Collections.emptyList();
    }

    @Override
    public Voucher update(Voucher voucher) {
        // 메모리에서 구현 X, 타임리프 구현
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        // 메모리에서 구현 X, 타임리프 구현
    }
}
