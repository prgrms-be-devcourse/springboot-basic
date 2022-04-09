package org.programmers.kdtspringvoucherweek1.voucher.repository;

import org.programmers.kdtspringvoucherweek1.voucher.Voucher;

public interface VoucherRepository {
    void save(Voucher voucher);

    void findByIdAll();
}
