package org.programmers.voucher;

import java.util.UUID;

public record VoucherRequest(Long discountValue, VoucherType voucherType) {}