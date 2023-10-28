package org.prgms.springbootbasic.repository;

import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<VoucherPolicy> findById(UUID voucherId);
    List<VoucherPolicy> findAll();
    VoucherPolicy create(VoucherPolicy voucherPolicy);
    Optional<VoucherPolicy> deleteById(UUID voucherId);
    void deleteAll();
}
