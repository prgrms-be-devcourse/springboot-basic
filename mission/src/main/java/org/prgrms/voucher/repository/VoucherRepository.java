package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(Long voucherId);

    List<Voucher> findByTypeAndTerm(VoucherType voucherType, LocalDate after, LocalDate before);

    List<Voucher> findByTerm(LocalDate after, LocalDate before);

    List<Voucher> findByType(VoucherType voucherType);

    void deleteById(Long voucherId);
}
