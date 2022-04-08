package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Map;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.Voucher.VoucherMapper;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(
      VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public UUID create(VoucherMapper voucherMapper, long voucherDiscountData)
      throws VoucherException {
    var voucher = voucherMapper.getFactory().create(UUID.randomUUID(), voucherDiscountData);

    return voucher.getVoucherId();

  }

  public Map<UUID, Voucher> getAllVouchers() {
    return voucherRepository.getAllVouchers();
  }
}
