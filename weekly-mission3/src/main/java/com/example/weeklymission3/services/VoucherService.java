package com.example.weeklymission3.services;

import com.example.weeklymission3.models.Voucher;
import com.example.weeklymission3.models.VoucherResponseDto;
import com.example.weeklymission3.models.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {

    Voucher createVoucher(String type, int discount);

    List<VoucherResponseDto> getAllVouchers();

    Optional<Voucher> getVoucherById(UUID voucherId);

    List<Voucher> getVouchersByTime(LocalDateTime startDate, LocalDateTime endDate);

    List<Voucher> getVouchersByType(VoucherType type);

    void deleteAllVouchers();

}
