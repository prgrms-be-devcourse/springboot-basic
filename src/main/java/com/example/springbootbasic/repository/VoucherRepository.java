package com.example.springbootbasic.repository;

import com.example.springbootbasic.domain.voucher.Voucher;

import java.util.Optional;

public interface VoucherRepository {
    Long save(Long voucherId, Voucher voucher);
    Optional<Voucher> findVoucherById(Long id);
    Long getSequence();
}
