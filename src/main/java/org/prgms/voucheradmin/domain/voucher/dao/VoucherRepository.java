package org.prgms.voucheradmin.domain.voucher.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

public interface VoucherRepository {
    Voucher create(Voucher voucher) throws IOException;

    List<Voucher> findAll() throws IOException;

    default Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    default Voucher update(Voucher voucher){
        return null;
    }

    default void delete(Voucher voucher) {}
}
