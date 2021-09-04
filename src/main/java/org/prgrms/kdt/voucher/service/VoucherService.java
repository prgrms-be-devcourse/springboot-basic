package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {
  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public Voucher getVoucher(UUID voucherId) {
    return voucherRepository
      .findById(voucherId)
      .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
  }

  public void useVoucher(Voucher voucher) {

  }
}
