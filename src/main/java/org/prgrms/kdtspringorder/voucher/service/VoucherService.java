package org.prgrms.kdtspringorder.voucher.service;

import java.util.List;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdtspringorder.voucher.enums.VoucherPolicy;
import org.prgrms.kdtspringorder.voucher.repository.abstraction.VoucherRepository;

import java.util.UUID;

public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(
      VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public Voucher getVoucher(final UUID voucherId) {
    return voucherRepository
        .findById(voucherId)
        .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
  }

  public List<Voucher> getVouchers() {
    return voucherRepository.getVouchers();
  }

  public Voucher createVoucher(String voucherType) {
    VoucherPolicy policy = VoucherPolicy.of(voucherType);
    // 존재하지 않는 Policy 를 택할 가능성이 있다.
    // Checked Exception 을 발생시켜서 상위에서 조취를 취해야한다.
    Voucher voucher = new Voucher(policy);
    return voucherRepository.saveVoucher(voucher);
  }

  public void useVoucher(final Voucher voucher) {
    throw new UnsupportedOperationException();
  }
}
