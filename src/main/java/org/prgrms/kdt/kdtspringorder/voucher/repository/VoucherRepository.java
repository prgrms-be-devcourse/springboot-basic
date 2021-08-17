package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    public Optional<Voucher> findById(UUID voucherId);

}
