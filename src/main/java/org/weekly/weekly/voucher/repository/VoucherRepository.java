package org.weekly.weekly.voucher.repository;

import org.springframework.stereotype.Repository;
import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherRepository {
    private final Map<UUID, Voucher> storages = new ConcurrentHashMap<>();

    public void insert(Voucher voucher) {
        validateUUID(voucher.getVoucherId());
        storages.put(voucher.getVoucherId(), voucher);
    }

    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storages.get(voucherId));
    }

    public List<Voucher> findAll() {
        return storages.values().stream()
                .toList();
    }


    private void validateUUID(UUID uuid) {
        Optional<Voucher> voucherOptional = findById(uuid);
        if (voucherOptional.isPresent()) {
            throw new VoucherException(ExceptionMsg.VOUCHER_EXIST);
        }
    }
}
