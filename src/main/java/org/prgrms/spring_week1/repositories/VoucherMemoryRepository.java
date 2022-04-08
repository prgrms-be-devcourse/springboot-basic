package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherMemoryRepository implements VoucherRepository{
    ConcurrentHashMap<UUID,Voucher> voucherHashMap = new ConcurrentHashMap<>();


    @Override
    public void insert(Voucher voucher) {
        voucherHashMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return null;
    }

    @Override
    public void showAll() {

    }
}
