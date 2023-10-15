package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
//@Qualifier("file")
public class FileRepository implements VoucherRepository{
    @Override
    public void create(Voucher voucher) {}

    @Override
    public Voucher getById(UUID voucherId) {
        return null;
    }

    @Override
    public List<Voucher> voucherLists() {
        return null;
    }
}
