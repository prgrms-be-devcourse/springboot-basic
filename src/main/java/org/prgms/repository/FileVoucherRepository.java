package org.prgms.repository;

import org.prgms.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository{
    @Override
    public void save(Voucher voucher) {

    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
