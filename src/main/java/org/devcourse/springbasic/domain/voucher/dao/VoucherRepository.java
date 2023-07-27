package org.devcourse.springbasic.domain.voucher.dao;

import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCriteria(
            LocalDate creationStartDate,
            LocalDate creationEndDate,
            VoucherType voucherType
    );

    UUID save(Voucher voucher);

    void deleteById(UUID voucherId);
}