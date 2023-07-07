package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.exception.NotFoundVoucherException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<Voucher> findVoucherById(String id) {
        UUID voucherId = convertToUuid(id);

        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher update(Voucher voucher) {
        return save(voucher);
    }

    @Override
    public int delete(String id) {
        UUID voucherId = convertToUuid(id);
        Voucher deletedVoucher = storage.remove(voucherId);

        return deletedVoucher == null ? 0 : 1;
    }

    private UUID convertToUuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new NotFoundVoucherException(id);
        }
    }
}
