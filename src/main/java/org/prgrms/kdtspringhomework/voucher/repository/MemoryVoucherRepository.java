package org.prgrms.kdtspringhomework.voucher.repository;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public Voucher add(Voucher voucher) {
        //Voucher 추가하기
        vouchers.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
