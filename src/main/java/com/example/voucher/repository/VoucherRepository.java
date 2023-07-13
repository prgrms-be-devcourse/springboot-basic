package com.example.voucher.repository;

import com.example.voucher.domain.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findAll();
    Voucher insert(Voucher voucher);
}
