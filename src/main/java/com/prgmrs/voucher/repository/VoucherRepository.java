package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.wrapper.Username;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> getAssignedVoucherListByUsername(Username username);

    List<Voucher> getNotAssignedVoucherList();

    List<Voucher> getAssignedVoucherList();
}
