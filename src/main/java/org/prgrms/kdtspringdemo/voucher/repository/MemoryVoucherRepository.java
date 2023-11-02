package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.dto.VoucherRequestDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local"})
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage;

    public MemoryVoucherRepository() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void update(UUID voucherId, VoucherRequestDto voucherRequestDto) {

    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = storage.values().stream().toList();
        return voucherList;
    }

    @Override
    public List<Voucher> findUnallocatedVoucher() {
        return null;
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }
}
