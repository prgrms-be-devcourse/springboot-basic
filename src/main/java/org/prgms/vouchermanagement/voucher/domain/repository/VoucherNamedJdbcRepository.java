package org.prgms.vouchermanagement.voucher.domain.repository;

import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class VoucherNamedJdbcRepository implements VoucherRepository {
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void saveVoucher(UUID voucherId, Voucher voucher) {

    }

    @Override
    public Map<UUID, Voucher> getVoucherList() {
        return Collections.emptyMap();
    }
}
