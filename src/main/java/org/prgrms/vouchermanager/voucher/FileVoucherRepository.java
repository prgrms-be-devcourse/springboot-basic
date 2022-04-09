package org.prgrms.vouchermanager.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 아직 미구현
 */
public class FileVoucherRepository implements VoucherRepository{
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> getAll() {
        return null;
    }
}
