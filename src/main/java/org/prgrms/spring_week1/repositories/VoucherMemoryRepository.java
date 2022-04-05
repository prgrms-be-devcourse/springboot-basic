package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoucherMemoryRepository implements VoucherRepository{
    List<Voucher> voucherList = new ArrayList<>();

    @Override
    public void insert(Voucher voucher) {
        voucherList.add(voucher);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return null;
    }
}
