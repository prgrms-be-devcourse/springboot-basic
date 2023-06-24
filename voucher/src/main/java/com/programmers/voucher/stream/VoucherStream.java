package com.programmers.voucher.stream;

import com.programmers.voucher.domain.voucher.Voucher;

import java.util.Map;

public interface VoucherStream {

    Voucher save(Voucher voucher);

    Map<String, Voucher> findAll();
}
