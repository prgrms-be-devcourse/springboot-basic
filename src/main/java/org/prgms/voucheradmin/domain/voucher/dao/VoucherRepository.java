package org.prgms.voucheradmin.domain.voucher.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

public interface VoucherRepository {
    Voucher create(Voucher voucher) throws IOException;

    List<Voucher> findAll() throws IOException;

    default List<Voucher> findAllWithCondition(VoucherType voucherType) {
        return null;
    }

    default Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    default List<Voucher> findAllocatedVouchers(UUID customerId) {
        return null;
    }

    default Voucher update(Voucher voucher){
        return null;
    }

    default void delete(Voucher voucher) {}
}
