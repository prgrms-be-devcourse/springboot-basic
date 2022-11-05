package com.example.springbootbasic.repository;

import com.example.springbootbasic.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Long save(Long voucherId, Voucher voucher);
    List<Voucher> findAllVouchers();
    Long getSequence();
}
