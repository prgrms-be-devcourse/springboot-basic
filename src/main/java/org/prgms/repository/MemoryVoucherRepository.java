package org.prgms.repository;

import org.prgms.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("memory")
public class MemoryVoucherRepository implements VoucherRepository{
    private final List<Voucher> db = new ArrayList<>();

    public void save(Voucher voucher){
        db.add(voucher);
    }

    public List<Voucher> findAll(){
        return db;
    }
}
