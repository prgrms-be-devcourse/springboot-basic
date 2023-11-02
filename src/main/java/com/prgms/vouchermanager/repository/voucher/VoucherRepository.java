package com.prgms.vouchermanager.repository.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    void update(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    void deleteById(UUID id);

    void deleteAll();

    List<Voucher> findByDate(LocalDateTime startTime, LocalDateTime endTime);

    List<Voucher> findByType(VoucherType type);
}
