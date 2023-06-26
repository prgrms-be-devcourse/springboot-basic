package org.devcourse.springbasic.domain.voucher.dao;

import org.devcourse.springbasic.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher findById(UUID voucherId);
    List<Voucher> findAll();
    UUID save(Voucher voucher);
}