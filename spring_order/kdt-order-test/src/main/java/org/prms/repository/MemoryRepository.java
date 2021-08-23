package org.prms.repository;

import org.prms.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//@Primary
@Qualifier("memoryRepo")
public class MemoryRepository implements VoucherRepository{

//    ArrayList<Voucher> list=new ArrayList<>();

    private ConcurrentHashMap<UUID,Voucher> map=new ConcurrentHashMap<>();




    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(map.get(voucherId));
    }



    @Override
    public void insert(Voucher voucher) {
        map.put(voucher.getVoucherId(),voucher);
    }


    @Override
    public ConcurrentHashMap<UUID, Voucher> getList() {
        return map;
    }




}
