package org.prgms;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository{

    private List<Voucher> vouchers = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void save(Voucher voucher) {
        this.vouchers.add(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers;
    }
}
