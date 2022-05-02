package org.prgrms.springbootbasic.engine.repository;

import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.exception.VoucherException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<Voucher> findAll();

    default int count() {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Optional<Voucher> findById(UUID voucherId) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default List<Voucher> findByType(VoucherType voucherType) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default List<Voucher> findByCustomerId(UUID customerId) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Voucher insert(Voucher voucher) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Voucher update(Voucher voucher) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default void deleteAll() {
        throw new VoucherException("미완성 기능입니다.");
    }

    default void deleteById(UUID voucherId)  {
        throw new VoucherException("미완성 기능입니다.");
    }
}
