package org.prgrms.prgrmsspring.repository.voucher;

import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Voucher update(Voucher voucher);

    void delete(UUID voucherId);
}
