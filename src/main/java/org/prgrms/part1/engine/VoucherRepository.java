package org.prgrms.part1.engine;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}
