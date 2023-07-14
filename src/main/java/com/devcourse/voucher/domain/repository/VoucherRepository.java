package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    void deleteById(UUID id);
}
