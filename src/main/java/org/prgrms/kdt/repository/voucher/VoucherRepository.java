package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.*;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();

}
