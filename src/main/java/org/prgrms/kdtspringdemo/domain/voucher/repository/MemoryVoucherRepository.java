package org.prgrms.kdtspringdemo.domain.voucher.repository;

import org.prgrms.kdtspringdemo.domain.voucher.Voucher;

import java.util.List;

public class MemoryVoucherRepository implements VoucherRepository{

    @Override
    public void clear() {

    }

    @Override
    public String save(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
