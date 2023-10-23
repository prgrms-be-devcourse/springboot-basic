package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.PolicyStatus;
import org.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
     void create(UUID voucherId, long amountOrPercent, PolicyStatus policy);

     Voucher getById(UUID voucherId);

     List<Voucher> voucherLists();
}
