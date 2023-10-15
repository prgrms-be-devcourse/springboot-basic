package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    public void create(Voucher voucher);

    public Voucher getById(UUID voucherId);

    public List<Voucher> voucherLists();
}
