package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

}
