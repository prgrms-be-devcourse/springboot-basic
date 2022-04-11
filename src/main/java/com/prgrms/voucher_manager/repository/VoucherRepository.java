package com.prgrms.voucher_manager.repository;

import com.prgrms.voucher_manager.voucher.Voucher;

public interface VoucherRepository {

    void save(Voucher voucher);

    void findAll();

    int getRepositorySize();
}
