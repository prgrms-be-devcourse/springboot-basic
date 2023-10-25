package com.programmers.springbootbasic.domain.voucher.domain;

import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
