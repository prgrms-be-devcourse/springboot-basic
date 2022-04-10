package com.pppp0722.vouchermanagement.voucher.repository;


import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final List<Voucher> voucherList = new ArrayList<>();

    @Override
    public void insert(Voucher voucher) {
        voucherList.add(voucher);
    }

    @Override
    public List<Voucher> getVoucherList() {
        return voucherList;
    }

}
