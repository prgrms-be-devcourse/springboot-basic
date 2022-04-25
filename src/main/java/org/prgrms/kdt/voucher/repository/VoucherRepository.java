package org.prgrms.kdt.voucher.repository;

import java.util.Collection;
import java.util.UUID;
import org.prgrms.kdt.voucher.model.Voucher;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Collection<Voucher> findAll();

    Voucher update(UUID voucherId, long value);

    void deleteAll();

}
