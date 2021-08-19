package com.prgrms.devkdtorder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileVoucherRepository implements VoucherRepository {

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void save(Voucher voucher) {

    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
