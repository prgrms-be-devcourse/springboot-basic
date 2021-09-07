package org.prgrms.kdtspringorder.voucher.repository.abstraction;

import java.util.List;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
  public Optional<Voucher> findById(UUID voucherId);
  public List<Voucher> getVouchers();
  public Voucher saveVoucher(Voucher voucher);
}
