package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

public interface VoucherRepository {

  UUID save(Voucher voucher) throws VoucherException;

  Optional<Voucher> getVoucherById(UUID voucherId);

  List<Voucher> getAllVouchers();

  int deleteAll();
}
