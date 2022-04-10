package com.prgms.management.voucher.repository;

import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;
import com.prgms.management.voucher.exception.VoucherListEmptyException;
import com.prgms.management.voucher.exception.VoucherNotFoundException;
import com.prgms.management.voucher.exception.VoucherNotSaveException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local", "default"})
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher findById(UUID voucherId) throws VoucherException {
        Optional<Voucher> voucher = Optional.ofNullable(storage.get(voucherId));
        if (voucher.isEmpty()) {
            throw new VoucherNotFoundException();
        }
        return voucher.get();
    }

    @Override
    public List<Voucher> findAll() throws VoucherException {
        if (storage.isEmpty()) {
            throw new VoucherListEmptyException();
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher save(Voucher voucher) throws VoucherException {
        storage.put(voucher.getVoucherId(), voucher);
        try {
            return findById(voucher.getVoucherId());
        } catch (VoucherException e) {
            throw new VoucherNotSaveException();
        }
    }
}
