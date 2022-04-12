package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}
