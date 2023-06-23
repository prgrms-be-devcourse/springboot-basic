package com.prgrms.spring.infrastructure.voucher;

import com.prgrms.spring.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    Map<UUID, Voucher> findAll();
}
