package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileVoucherRepository implements VoucherRepository {
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> findAllVaucher() {
        return null;
    }
}
