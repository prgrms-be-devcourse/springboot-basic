package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> getAssignedVoucherListByUsername(String username);

    List<Voucher> getNotAssignedVoucherList();

    List<Voucher> getAssignedVoucherList();
}
