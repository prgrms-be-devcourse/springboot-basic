package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;

public interface VoucherRepository {

  UUID save(Voucher voucher) throws VoucherException;

  Optional<Voucher> getVoucher(UUID voucherId);

  Collection<Voucher> getAllVouchers();


}
