package org.prms.repository;

import org.prms.domain.Voucher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class MemoryRepository implements VoucherRepository{

    ArrayList<Voucher> list=new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        list.add(voucher);
    }

    @Override
    public ArrayList<Voucher> getList() {
        return list;
    }
}
