package org.prms.repository;

import org.prms.domain.Voucher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryRepository implements VoucherRepository{

//    ArrayList<Voucher> list=new ArrayList<>();

    ConcurrentHashMap<UUID,Voucher> list=new ConcurrentHashMap<>();


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

//    @Override
//    public void insert(Voucher voucher) {
//        list.add(voucher);
//    }

    @Override
    public void insert(UUID voucherID,Voucher voucher) {
        list.put(voucherID,voucher);
    }



//    @Override
//    public ArrayList<Voucher> getList() {
//        return list;
//    }

    @Override
    public ConcurrentHashMap<UUID, Voucher> getList() {
        return list;
    }




}
