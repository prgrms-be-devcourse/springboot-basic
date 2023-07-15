package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;

import java.util.List;

public interface VoucherService {
    VoucherResponseDto create(VoucherType userVoucherType, long amount);

    List<VoucherResponseDto> getAllVoucher();
}
