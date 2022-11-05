package com.example.springbootbasic.domain.repository;

import com.example.springbootbasic.domain.voucher.Voucher;

import java.util.Optional;

public interface VoucherRepository {
    Long save(Voucher voucher);
    Optional<Voucher> findVoucherById(Long id);
}
