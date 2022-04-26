package org.prgrms.kdt.voucher.repository;

import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.voucher.model.Voucher;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Voucher update(UUID voucherId, long value);

    void deleteAll();

}
