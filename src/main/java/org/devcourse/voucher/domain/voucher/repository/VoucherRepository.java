package org.devcourse.voucher.domain.voucher.repository;

import org.devcourse.voucher.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();
}
