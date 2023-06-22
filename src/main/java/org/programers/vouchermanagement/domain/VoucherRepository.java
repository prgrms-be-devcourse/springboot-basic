package org.programers.vouchermanagement.domain;

import org.programers.vouchermanagement.exception.NoSuchVoucherException;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    default Voucher getById(UUID id) {
        return findById(id).orElseThrow(NoSuchVoucherException::new);
    }
}
