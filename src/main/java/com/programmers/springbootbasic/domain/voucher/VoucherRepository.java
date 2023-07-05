package com.programmers.springbootbasic.domain.voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

}
