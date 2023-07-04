package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.global.exception.NoSuchEntityException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    default Voucher getById(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchEntityException("존재하지 않는 바우처입니다."));
    }

    List<Voucher> findAll();

    void update(Voucher voucher);

    void deleteById(UUID id);
}
