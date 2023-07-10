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
    public Optional<Voucher> save(Voucher voucher) {
        voucherStorage.add(voucher);
        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        if (!voucherStorage.isEmpty()) {
            return Collections.unmodifiableList(voucherStorage);
        }
        return Collections.emptyList();
    }
}
