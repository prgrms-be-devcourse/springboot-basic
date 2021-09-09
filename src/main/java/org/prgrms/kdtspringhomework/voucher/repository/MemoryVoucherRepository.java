package org.prgrms.kdtspringhomework.voucher.repository;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> Vouchers = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(Vouchers.values());
    }

    @Override
    public Voucher add(Voucher voucher) {
        //Voucher 추가하기
        Vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
