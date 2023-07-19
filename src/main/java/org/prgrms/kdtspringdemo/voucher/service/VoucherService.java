package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponse;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    VoucherResponse create(VoucherType userVoucherType, long amount);

    VoucherResponse findById(UUID voucherId);

    List<VoucherResponse> findAll();

    VoucherResponse update(UUID voucherId, VoucherType voucherType, long amount);

    void delete(UUID voucherId);
}
