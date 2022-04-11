package org.prgrms.kdtspringdemo.service;


import org.prgrms.kdtspringdemo.domain.voucher.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherType;

import java.util.List;

public interface VoucherService {
    String save(VoucherType voucherType, long value);
    List<Voucher> findAll();
}
