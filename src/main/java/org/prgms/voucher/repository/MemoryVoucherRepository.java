package org.prgms.voucher.repository;

import org.prgms.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile({"local", "default"})
public class MemoryVoucherRepository implements VoucherRepository{
    private final List<Voucher> db = new ArrayList<>();

    public void save(Voucher voucher){
        db.add(voucher);
    }

    public List<Voucher> findAll(){
        return db;
    }
}
