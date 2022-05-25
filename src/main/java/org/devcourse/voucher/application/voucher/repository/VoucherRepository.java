package org.devcourse.voucher.application.voucher.repository;

import org.devcourse.voucher.application.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();
}
