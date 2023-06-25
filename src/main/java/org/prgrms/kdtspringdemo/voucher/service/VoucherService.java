package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher createVoucher(VoucherType voucherType, long discount);

    List<Voucher> getAllVoucher();
}
