package org.prgms.springbootbasic.repository.voucher;

import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<VoucherPolicy> findById(UUID voucherId);
    List<VoucherPolicy> findAll();
    VoucherPolicy upsert(VoucherPolicy voucherPolicy);
    void deleteById(UUID voucherId);
    void deleteAll();
}
