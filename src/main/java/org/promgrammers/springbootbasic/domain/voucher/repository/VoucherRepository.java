package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.exception.repository.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    default Voucher getVoucherById(UUID voucherId) {
        return findById(voucherId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 바우처입니다."));
    }

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID voucherId);
}
