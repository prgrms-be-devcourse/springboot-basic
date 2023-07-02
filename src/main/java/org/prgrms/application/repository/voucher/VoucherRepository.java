package org.prgrms.application.repository.voucher;

import org.prgrms.application.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(Long voucherId);

    void deleteAll();
}
