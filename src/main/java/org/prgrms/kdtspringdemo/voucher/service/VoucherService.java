package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponse;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    VoucherResponse create(VoucherType type, long amount);

    VoucherResponse findById(UUID id);

    List<VoucherResponse> findAll();

    VoucherResponse upsert(UUID id, VoucherType type, long amount);

    void delete(UUID id);
}
