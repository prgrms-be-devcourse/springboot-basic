package org.prgms.springbootbasic.repository.voucher;

import org.prgms.springbootbasic.domain.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findByPolicyBetweenLocalDateTime(String voucherPolicy, LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<Voucher> findAll();
    Voucher upsert(Voucher voucher);
    void deleteById(UUID voucherId);
    void deleteAll();
}
