package org.programs.kdt.Voucher.repository;

import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private Map<UUID, Voucher> storage = new LinkedHashMap<>();
    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        boolean isVoucherId = storage.containsKey(voucher.getVoucherId());
        if (isVoucherId) {
            throw new DuplicationException(ErrorCode.DUPLICATION_VOUCHER_ID);
        }

        synchronized (this) {
            storage.put(voucher.getVoucherId(), voucher);
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return storage.values().stream().filter(voucher -> voucher.getVoucherType().equals(voucherType)).collect(Collectors.toList());
    }

    @Override
    public boolean existId(UUID customerId) {
        return storage.containsKey(customerId);
    }


    @Override
    public Voucher update(Voucher updateVoucher) {
        boolean isVoucherId = storage.containsKey(updateVoucher.getVoucherId());
        if (!isVoucherId) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID);
        }
        storage.put(updateVoucher.getVoucherId(), updateVoucher);
        return updateVoucher;
    }

    @Override
    public void deleteAll() {
        storage = new LinkedHashMap<>();
    }

    @Override
    public void delete(UUID uuid) {
        boolean isVoucherId = storage.containsKey(uuid);
        if (!isVoucherId) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID);
        }
        storage.remove(uuid);
    }
}
