package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    List<Voucher> findAll();

    Voucher save(Voucher voucher);

}
