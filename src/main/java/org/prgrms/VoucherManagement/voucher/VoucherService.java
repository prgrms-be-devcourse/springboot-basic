package org.prgrms.VoucherManagement.voucher;

import org.prgrms.VoucherManagement.voucher.repository.VoucherRepository;
import org.prgrms.VoucherManagement.voucher.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
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
