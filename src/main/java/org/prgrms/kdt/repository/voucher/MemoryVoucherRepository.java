package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherList;
import org.prgrms.kdt.model.voucher.VoucherMap;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Optional<Voucher> insertVoucher(Voucher voucher) {
        return Optional.ofNullable(storage.put(voucher.getVoucherId(), voucher));
    }

    @Override
    public VoucherMap getVoucherList() {
        return new VoucherMap(storage);
    }

    @Override
    public void deleteVoucherById(UUID voucherId) {
    }

    @Override
    public Optional<Voucher> getByVoucherId(UUID voucherId) {
        return null;
    }

    @Override
    public VoucherList getVoucherListOwnerIdIsEmpty() {
        return null;
    }

    @Override
    public Optional<Voucher> updateVoucherOwner(UUID voucherId, UUID customerId) {
        return null;
    }

    @Override
    public Optional<Voucher> getVoucherNotProvided(UUID voucherId) {
        return null;
    }

    @Override
    public void deleteAllVouchers() {

    }

    @Override
    public VoucherList getVoucherListByVoucherType(int voucherType) {
        return null;
    }
}
