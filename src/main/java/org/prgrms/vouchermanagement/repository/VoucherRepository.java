package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.PolicyStatus;
import org.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    public void load();

    public void create(UUID voucherId, long amountOrPercent, PolicyStatus policy);

    public Voucher getById(UUID voucherId);

    public List<Voucher> voucherLists();
}
