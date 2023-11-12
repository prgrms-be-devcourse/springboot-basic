package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    Optional<Voucher> findByName(String name);

    List<Voucher> findByNameLike(String name);

    List<Voucher> findByTypeAndDates(String type, LocalDate startDate, LocalDate endDate);

    Voucher save(Voucher voucher);

    int delete(UUID id);
}
