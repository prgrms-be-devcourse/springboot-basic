package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository{

    private Map<UUID,Voucher> voucherMap = new HashMap<>();

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        this.voucherMap.put(voucher.getVoucherId(), voucher);
    }

}
