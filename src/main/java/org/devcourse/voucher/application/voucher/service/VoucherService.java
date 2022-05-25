package org.devcourse.voucher.application.voucher.service;

import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.devcourse.voucher.application.voucher.model.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(VoucherType voucherType, long discount);

    List<Voucher> recallAllVoucher();
}
