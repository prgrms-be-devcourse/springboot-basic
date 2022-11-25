package com.programmers.voucher.service;

import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VoucherService {
    VoucherDto register(String voucherType, String value);

    VoucherDto getVoucher(UUID voucherId);

    List<VoucherDto> findAll();

    List<VoucherDto> searchVouchersByCustomerId(UUID customerId);

    void deleteAll();

    void deleteVoucher(UUID voucherId);

    List<VoucherDto> getTypeVoucher(String type);

    List<VoucherDto> findVoucherByPeriod(LocalDateTime from, LocalDateTime to);

    default VoucherDto entityToDto(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherId(),
                voucher.getType(),
                voucher.getValue(),
                voucher.isAssigned()
        );
    }
}
