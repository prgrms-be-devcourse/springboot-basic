package com.programmers.vouchermanagement.voucher.domain;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
