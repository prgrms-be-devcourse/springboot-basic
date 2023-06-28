package com.example.demo.voucher.domain.repository;

import com.example.demo.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    void insert(Voucher voucher);
}
