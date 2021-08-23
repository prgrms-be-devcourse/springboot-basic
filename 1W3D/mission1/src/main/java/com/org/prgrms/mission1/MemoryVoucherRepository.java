package com.org.prgrms.mission1;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("Memory")
public class MemoryVoucherRepository implements VoucherRepository {

    List<Voucher> vouchers = new ArrayList<>();

    @Override
    public List<Voucher> findAll() {    // HashMap value 값 Arraylist로 리턴

        return vouchers;
    }


    @Override
    public Voucher insert(Voucher voucher) {  // hashMap 삽입
        vouchers.add(voucher);

        return voucher;
    }


}
