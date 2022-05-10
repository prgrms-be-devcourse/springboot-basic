package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.Duration;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(long id);

    void deleteById(long id);

    List<Voucher> findByType(VoucherType type);

    List<Voucher> findByDuration(Duration duration);

    List<Voucher> findByTypeAndDuration(VoucherType type, Duration duration);
}
