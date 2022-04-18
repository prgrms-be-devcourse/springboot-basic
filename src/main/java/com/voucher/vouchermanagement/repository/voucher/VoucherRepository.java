package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    void insert(Voucher voucher);

    List<Voucher> findAll();
}
