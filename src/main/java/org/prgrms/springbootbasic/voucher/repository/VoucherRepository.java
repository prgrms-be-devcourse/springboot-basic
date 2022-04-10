package org.prgrms.springbootbasic.voucher.repository;

import org.prgrms.springbootbasic.voucher.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
  Optional<Voucher> findById(UUID voucherId);

  Voucher insert(Voucher voucher);

  String findAll();
}
