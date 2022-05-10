package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByType(String type);

    List<Voucher> findByDate(LocalDate start, LocalDate end);

    List<Voucher> findByDateAndType(LocalDate start, LocalDate end, String type);

    Voucher update(Voucher voucher);

    Voucher delete(Voucher voucher);

    int count();

}
