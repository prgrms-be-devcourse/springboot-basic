package com.example.voucher_manager.domain.repository;

import com.example.voucher_manager.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    List<Voucher> findAll();
    Voucher insert(Voucher voucher);
    void clear();
}