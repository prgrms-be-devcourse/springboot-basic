package org.programmers.devcourse.voucher.engine.voucher;

import java.time.LocalDateTime;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.springframework.lang.Nullable;

public interface VoucherFactory {

  Voucher create(UUID id, long discountDegree, @Nullable LocalDateTime createdAt) throws VoucherException;
}
