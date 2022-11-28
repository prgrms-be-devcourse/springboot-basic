package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    void createVoucher(String customerEmail, String type, long discount);

    List<Voucher> getAllVouchers();

    List<Voucher> getVouchersByCustomerEmail(String customerEmail);

    List<Voucher> getVouchersByDate(String customerEmail, String date);

    void update(UUID voucherId, long discount);

    void deleteById(UUID voucherId);

}
