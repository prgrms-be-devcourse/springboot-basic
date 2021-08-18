package org.prgrms.kdtspringdemo.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId);

    public Voucher insert(Voucher voucher);

    public List<Voucher> findAll();
}
