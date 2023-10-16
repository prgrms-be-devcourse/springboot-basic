package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;

import java.util.List;

public interface VoucherRepository {
    void insert(Voucher voucher);

    List<Voucher> findAll();
}
