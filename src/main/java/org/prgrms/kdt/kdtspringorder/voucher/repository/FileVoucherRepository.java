package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileVoucherRepository implements VoucherRepository{

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {

    }
    
}
