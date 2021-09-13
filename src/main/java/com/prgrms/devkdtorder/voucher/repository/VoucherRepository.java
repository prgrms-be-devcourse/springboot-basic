package com.prgrms.devkdtorder.voucher.repository;

import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.domain.VoucherType;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();

    default void deleteById(UUID voucherId) {
        throw new UnsupportedOperationException();
    }

    default List<Voucher> findByCreatedAt(LocalDateTime from, LocalDateTime to) {
        throw new UnsupportedOperationException();
    }

    default List<Voucher> findByVoucherType(VoucherType voucherType) {
        throw new UnsupportedOperationException();
    }
}
