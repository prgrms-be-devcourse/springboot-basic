package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher createVoucher(UUID voucherId, VoucherType voucherType, long amount, LocalDateTime createdAt, UUID memberId);

    List<Voucher> getAllVouchers();

    Voucher getVoucherById(UUID voucherId);

    List<Voucher> getVouchersByMemberId(UUID memberId);

    Voucher updateVoucher(UUID voucherId, VoucherType type, long amount);

    Voucher deleteVoucher(UUID voucherId);

    void deleteAllVouchers();
}
