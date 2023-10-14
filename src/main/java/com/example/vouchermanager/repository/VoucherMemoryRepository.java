package com.example.vouchermanager.repository;

import com.example.vouchermanager.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VoucherMemoryRepository {

    List<Voucher> vouchers = new ArrayList<>();

    public void create(Voucher voucher) {
        vouchers.add(voucher);
    }

    public List<Voucher> list() { return vouchers; }
}
