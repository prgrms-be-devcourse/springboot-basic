package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map getVoucherList() {
        return storage;
    }

    @Override
    public Voucher delete(Voucher voucher) {
        return null;
    }

    @Override
    public Voucher getByVoucherId(UUID voucherId) {
        return null;
    }

    @Override
    public List<Voucher> getVoucherListOwnerIdIsEmpty() {
        return null;
    }

    @Override
    public Voucher updateVoucherOwner(UUID voucherId, UUID customerId) {
        return null;
    }

    @Override
    public Voucher getByVoucherNotProvided(UUID voucherId) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
