package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.common.exception.DataModifyingNotSupportedException;
import com.prgrms.springbootbasic.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {

    void save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    default void update(Voucher voucher) {
        throw new DataModifyingNotSupportedException("update");
    }

    default void delete(UUID id) {
        throw new DataModifyingNotSupportedException("delete");
    }
}
