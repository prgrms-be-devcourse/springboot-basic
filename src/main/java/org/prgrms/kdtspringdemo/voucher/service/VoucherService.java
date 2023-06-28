package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;

import java.util.List;

public interface VoucherService {
    VoucherDto create(VoucherDto voucherDto);

    List<VoucherDto> getAllVoucher();
}
