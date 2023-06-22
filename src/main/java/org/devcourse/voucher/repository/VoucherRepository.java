package org.devcourse.voucher.repository;

import org.devcourse.voucher.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
