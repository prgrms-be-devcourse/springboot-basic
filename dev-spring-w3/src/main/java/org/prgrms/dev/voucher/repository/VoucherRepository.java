package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    // Optioanl<T>로 없을 수 있는 Entity 에 대해 NPE 방지
    Optional<Voucher> findById(UUID voucherId);
}
