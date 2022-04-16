package org.prgrms.kdtspringdemo.service;


import org.prgrms.kdtspringdemo.domain.voucher.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherType;

import java.util.List;

public interface VoucherService {
    Voucher save(VoucherType voucherType, long discountAmount);
    List<Voucher> findAll();
}
