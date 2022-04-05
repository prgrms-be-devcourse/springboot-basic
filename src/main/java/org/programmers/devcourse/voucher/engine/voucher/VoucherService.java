package org.programmers.devcourse.voucher.engine.voucher;

import java.util.UUID;
import org.programmers.devcourse.voucher.engine.voucher.Voucher.Type;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(
      VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public UUID create(Type voucherType, long voucherDiscountData) {

    return voucherRepository.insert(voucherType.createVoucher(voucherDiscountData));
  }
}
