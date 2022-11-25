package com.programmers.voucher.service;

import com.programmers.voucher.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VoucherService {
    Voucher register(String voucherType, String value);

    Voucher getVoucher(UUID voucherId);

    List<Voucher> findAll();

    List<Voucher> searchVouchersByCustomerId(UUID customerId);

    void deleteAll();

    void deleteVoucher(UUID voucherId);

    List<Voucher> getTypeVoucher(String type);

    List<Voucher> findVoucherByPeriod(LocalDateTime from, LocalDateTime to);
}
