package me.kimihiqq.vouchermanagement.domain.voucher.repository;

import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    void deleteById(UUID voucherId);
    List<Voucher> findAllByCreationDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Voucher> findAllByType(VoucherTypeOption type);

}