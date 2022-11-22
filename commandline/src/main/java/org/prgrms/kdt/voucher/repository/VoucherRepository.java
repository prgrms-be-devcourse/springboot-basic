package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher insert(String type, long discountDegree);

    List<Voucher> findAll();

    Voucher findById(long voucherId);

    void update(long voucherId, long discountDegree);

    void deleteAll();
}
