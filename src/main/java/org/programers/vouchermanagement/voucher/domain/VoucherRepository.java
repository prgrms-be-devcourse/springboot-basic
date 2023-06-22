package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.voucher.exception.NoSuchVoucherException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    default Voucher getById(UUID id) {
        return findById(id).orElseThrow(NoSuchVoucherException::new);
    }

    List<Voucher> findAll();
}
