package com.pppp0722.vouchermanagement.voucher.service;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {

    Optional<Voucher> createVoucher(UUID voucherId, VoucherType voucherType, long amount,
        UUID memberId);

    List<Voucher> getAllVouchers();

    Optional<Voucher> getVoucherById(UUID voucherId);

    List<Voucher> getVouchersByMemberId(UUID memberId);

    Optional<Voucher> updateVoucher(UUID voucherId, VoucherType type, long amount);

    Optional<Voucher> deleteVoucher(UUID voucherId);
}
