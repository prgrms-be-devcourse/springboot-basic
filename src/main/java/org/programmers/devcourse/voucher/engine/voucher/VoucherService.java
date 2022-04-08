package org.programmers.devcourse.voucher.engine.voucher;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherDataOutOfRangeException;
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
      throws VoucherException, ReflectiveOperationException {
    var voucherClass = voucherMapper.getVoucherClass();

    try {
      var voucher = (Voucher) voucherClass.getMethod("from", long.class)
          .invoke(voucherClass, voucherDiscountData);
      return voucherRepository.insert(voucher);
    } catch (InvocationTargetException e) {
      if (e.getCause() instanceof VoucherDataOutOfRangeException) {
        throw (VoucherDataOutOfRangeException) e.getCause();
      }

    }
    throw new VoucherException("Voucher Creation Failed");

  }

  public Map<UUID, Voucher> getAllVouchers() {
    return voucherRepository.getAllVouchers();
  }
}
