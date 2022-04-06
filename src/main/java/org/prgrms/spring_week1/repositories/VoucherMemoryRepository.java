package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class VoucherMemoryRepository implements VoucherRepository{
    List<Voucher> voucherList = new ArrayList<>();

    public VoucherMemoryRepository(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    @Override
    public void insert(Voucher voucher) {
        voucherList.add(voucher);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return null;
    }
}
