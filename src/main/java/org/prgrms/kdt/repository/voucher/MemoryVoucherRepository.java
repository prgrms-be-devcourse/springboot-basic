package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Voucher insertVoucher(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map getVoucherList() {
        return storage;
    }

    @Override
    public UUID deleteVoucherById(UUID voucherId) {
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
    public Voucher getVoucherNotProvided(UUID voucherId) {
        return null;
    }

    @Override
    public void deleteAllVouchers() {

    }
}
