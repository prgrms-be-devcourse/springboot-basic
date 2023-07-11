package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    VoucherResponseDto create(VoucherType userVoucherType, long amount);

    VoucherResponseDto findById(UUID voucherId);

    List<VoucherResponseDto> findAll();

    VoucherResponseDto update(UUID voucherId, VoucherType voucherType, long amount);

    VoucherResponseDto delete(UUID voucherId);
}
