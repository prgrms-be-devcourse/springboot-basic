package org.prgrms.springorder.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.Voucher;
import org.prgrms.springorder.domain.VoucherType;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();

}
