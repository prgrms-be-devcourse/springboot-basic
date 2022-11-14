package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository {
    void addVoucher(Voucher voucher);

    List<Voucher> findAll();
}