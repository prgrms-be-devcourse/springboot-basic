package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(long id);

    void deleteById(long id);

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findByTypeAndCreatedDateBetween(VoucherType type, LocalDate startDate, LocalDate endDate);

    List<Voucher> findAllByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
