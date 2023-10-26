package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
     int create(UUID voucherId, DiscountPolicy discountPolicy);

     List<Voucher> voucherLists();

     void update(UUID voucherId, long amount);

     int deleteAll();

     Voucher getById(UUID voucherId);
}
