package com.prgrms.vouchermanagement.core.voucher.repository;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final List<Voucher> voucherArrayList = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Voucher save(Voucher voucher) {
        voucher.setVoucherID(idGenerator.getAndIncrement());
        voucherArrayList.add(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherArrayList;
    }
}
