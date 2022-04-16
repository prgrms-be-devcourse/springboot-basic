package org.prgrms.kdtspringdemo.domain.voucher.repository;


import org.prgrms.kdtspringdemo.domain.voucher.Voucher;

import java.util.List;


public interface VoucherRepository {
    void clear();
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
}
