package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    void saveAll(List<Voucher> vouchers);

    List<Voucher> findAll();
}
