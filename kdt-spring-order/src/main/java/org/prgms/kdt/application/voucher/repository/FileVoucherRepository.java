package org.prgms.kdt.application.voucher.repository;

import org.prgms.kdt.application.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository{

    @Override
    public UUID save(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
