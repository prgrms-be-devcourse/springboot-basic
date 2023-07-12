package com.programmers.voucher.stream.voucher;

import com.programmers.voucher.domain.voucher.Voucher;

import java.util.Map;

public interface VoucherStream {

    Voucher save(Voucher voucher);

    Map<String, Voucher> findAll();

    default Voucher findById(String voucherId) {
        return null;
    }

    default Voucher update(Voucher voucher) {
        return null;
    }

    default void deleteById(String voucherId) {
    }

    default void deleteAll() {
    }
}
