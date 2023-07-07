package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.util.ExceptionMessage;

import java.util.List;
import java.util.UUID;

public interface VouchersStorage {

    void save(Voucher voucher);
    List<Voucher> findAll();

    default Voucher findById(UUID voucherId) {
        throw new IllegalArgumentException(ExceptionMessage.NOT_SUPPORTED.getMessage());
    }

    default void update(Voucher voucher) {
        throw new IllegalArgumentException(ExceptionMessage.NOT_SUPPORTED.getMessage());
    }

    default void deleteOne(UUID voucherId) {
        throw new IllegalArgumentException(ExceptionMessage.NOT_SUPPORTED.getMessage());
    }

}
