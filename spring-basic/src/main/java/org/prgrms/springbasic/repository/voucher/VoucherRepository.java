package org.prgrms.springbasic.repository.voucher;

import org.prgrms.springbasic.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    int countStorageSize();

    void clear();
}