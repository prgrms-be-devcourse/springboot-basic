package org.prgrms.kdt.repository.voucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.exception.DuplicateObjectException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> find(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    void delete(Voucher voucher);

    void deleteById(UUID voucherId);

    void deleteAll();
}