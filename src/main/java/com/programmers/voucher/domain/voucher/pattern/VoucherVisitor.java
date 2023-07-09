package com.programmers.voucher.domain.voucher.pattern;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;

public interface VoucherVisitor {
    void visit(FixedAmountVoucher voucher);
    void visit(PercentDiscountVoucher voucher);
}
