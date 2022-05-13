package org.programmers.devcourse.voucher.engine.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

public interface VoucherService {

  Voucher create(String voucherTypeId, long voucherDiscountData) throws VoucherException;

  List<Voucher> getAllVouchers();

  Optional<VoucherType> mapTypeToMapper(String type);

  void remove(UUID voucherId);

  Voucher getVoucherById(UUID voucherId);

  List<Voucher> getVouchersByType(String type);

  List<Voucher> getVouchersByCreatedAt(LocalDateTime date);
}
