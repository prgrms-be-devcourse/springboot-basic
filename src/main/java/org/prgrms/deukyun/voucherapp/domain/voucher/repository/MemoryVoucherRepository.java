package org.prgrms.deukyun.voucherapp.domain.voucher.repository;

import org.prgrms.deukyun.voucherapp.domain.common.repository.MemoryRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * 메모리 바우처 리포지토리
 */
@Repository
public class MemoryVoucherRepository extends MemoryRepository<Voucher, UUID> implements VoucherRepository {

    public MemoryVoucherRepository(Supplier<UUID> uuidSupplier) {
        super(uuidSupplier);
    }
}
