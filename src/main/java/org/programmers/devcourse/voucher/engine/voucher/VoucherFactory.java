package org.programmers.devcourse.voucher.engine.voucher;

import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherDataOutOfRangeException;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

public interface VoucherFactory {


  Voucher create(UUID id, long discountDegree) throws VoucherDataOutOfRangeException;
}
