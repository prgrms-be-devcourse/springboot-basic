package com.programmers.vouchermanagement.voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);
    List<Voucher> findAll();
}
