package org.prgrms.kdt.infrastructure.voucher;

import org.prgrms.kdt.domain.voucher.domain.Voucher;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class VoucherMemoryRepository implements VoucherRepository {
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return null;
    }

    @Override
    public void save(Voucher voucher) {

    }
}
