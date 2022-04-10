package com.example.voucher_manager.domain.repository;

import com.example.voucher_manager.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    List<Voucher> findAll();
    Optional<Voucher> insert(Voucher voucher);
    void clear();
}