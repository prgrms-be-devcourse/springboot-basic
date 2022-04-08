package org.programmers.devcourse.voucher.engine.voucher;

import java.util.UUID;

public interface VoucherFactory {


  Voucher create(UUID id, long discountDegree);
}
