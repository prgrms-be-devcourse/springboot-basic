package com.programmers.springbootbasic.domain.voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Optional<Voucher> save(Voucher voucher);

    List<Voucher> findAll();

}
