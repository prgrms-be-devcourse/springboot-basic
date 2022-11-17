package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAllVouchers();
    void deleteAll();
}
