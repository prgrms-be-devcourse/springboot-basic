package org.prgrms.kdt.voucher;

import org.prgrms.kdt.VoucherType;

import java.util.*;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    int count();
    List<Voucher> getListByType(VoucherType voucherType);
    void deleteAll();
}
