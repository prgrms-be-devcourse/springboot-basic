package com.programmers.voucher.stream.voucher;

import com.programmers.voucher.domain.voucher.Voucher;

import java.util.Map;

public interface VoucherStream {

    Voucher save(Voucher voucher);

    Map<String, Voucher> findAll();

    default Voucher findById(String voucherId) {
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }

    default Voucher update(Voucher voucher) {
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }

    default void deleteById(String voucherId) {
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }

    default void deleteAll() {
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }
}
