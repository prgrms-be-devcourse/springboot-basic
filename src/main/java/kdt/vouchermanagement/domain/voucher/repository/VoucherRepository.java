package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    void deleteById(Long voucherId);

    Optional<Voucher> findById(Long voucherId);

    List<Voucher> findByTypeAndDate(VoucherType type, LocalDate date);

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findByDate(LocalDate date);
}
