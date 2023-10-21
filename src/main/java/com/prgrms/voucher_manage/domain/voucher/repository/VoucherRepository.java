package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
