package org.prgrms.kdtspringdemo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId);

    public void insert(Voucher voucher);

    public List<Voucher> findAll();
}
