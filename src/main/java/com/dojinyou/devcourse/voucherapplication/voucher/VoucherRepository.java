package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(long id);

    void deleteById(long id);

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findByTypeAndDate(VoucherType type, LocalDate fromDate, LocalDate toDate);

    List<Voucher> findAllByDate(LocalDate startDate, LocalDate endDate);
}
