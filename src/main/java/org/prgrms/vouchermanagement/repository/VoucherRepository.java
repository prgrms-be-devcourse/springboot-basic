package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.DiscountPolicy;
import org.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
     void create(UUID voucherId, DiscountPolicy discountPolicy);

     Voucher getById(UUID voucherId);

     List<Voucher> voucherLists();
}
