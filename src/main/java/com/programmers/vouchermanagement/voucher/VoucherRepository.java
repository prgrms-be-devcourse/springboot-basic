package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    void save(Voucher voucher);
    List<Voucher> findAll();
}
