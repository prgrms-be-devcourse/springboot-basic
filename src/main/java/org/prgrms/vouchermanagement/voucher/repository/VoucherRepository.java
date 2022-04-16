package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
  Optional<Voucher> findById(UUID voucherId);

  Voucher insert(Voucher voucher);

  List<Voucher> findAll();

  public int count();

  public void deleteAll();
}
