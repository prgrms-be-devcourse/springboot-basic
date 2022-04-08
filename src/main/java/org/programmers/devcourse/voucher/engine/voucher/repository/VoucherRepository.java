package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;

public interface VoucherRepository {

  UUID insert(Voucher voucher) throws VoucherException;

  Optional<Voucher> getVoucher(UUID voucherId);

  Map<UUID, Voucher> getAllVouchers();
}
