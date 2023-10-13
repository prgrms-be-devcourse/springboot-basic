package com.example.vouchermanager.repository;

import com.example.vouchermanager.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryRepository{

    List<Voucher> vouchers = new ArrayList<>();

    public void create(Voucher voucher) {
        vouchers.add(voucher);
    }
}
