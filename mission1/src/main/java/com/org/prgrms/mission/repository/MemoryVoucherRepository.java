package com.org.prgrms.mission.repository;


import com.org.prgrms.mission.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
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
