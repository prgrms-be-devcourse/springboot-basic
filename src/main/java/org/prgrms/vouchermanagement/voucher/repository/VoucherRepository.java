package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
  Voucher insert(Voucher voucher);

  List<Voucher> findAll();

  List<Voucher> findByVoucherType(VoucherType voucherType);

  Optional<Voucher> findById(UUID voucherId);

  public void deleteAll();

  public void deleteById(UUID voucherId);

  public void updateById(UUID voucherId);

  public int count();
}
