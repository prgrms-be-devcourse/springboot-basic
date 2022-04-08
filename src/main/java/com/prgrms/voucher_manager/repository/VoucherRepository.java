package com.prgrms.voucher_manager.repository;

import com.prgrms.voucher_manager.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();
}
