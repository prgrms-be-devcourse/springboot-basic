package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

  Customer findOwnerById(UUID voucherId);

  Optional<Voucher> findById(UUID voucherId);

  Voucher save(Voucher voucher);

  List<Voucher> findAll();
}
