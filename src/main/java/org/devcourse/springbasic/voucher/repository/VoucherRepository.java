package org.devcourse.springbasic.voucher.repository;

import org.devcourse.springbasic.voucher.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    UUID create(Voucher voucherId);

}