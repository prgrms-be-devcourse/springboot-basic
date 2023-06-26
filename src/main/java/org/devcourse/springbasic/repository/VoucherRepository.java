package org.devcourse.springbasic.repository;

import org.devcourse.springbasic.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    UUID save(Voucher voucher);
}