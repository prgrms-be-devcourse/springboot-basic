package org.devcourse.voucher.voucher.service;

import org.devcourse.voucher.model.ListType;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(VoucherType voucherType, long discount);

    List<Voucher> recallAllVoucher();
}
