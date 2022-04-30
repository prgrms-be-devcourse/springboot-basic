package com.pppp0722.vouchermanagement.voucher.repository;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> createVoucher(Voucher voucher);

    List<Voucher> readAllVouchers();

    Optional<Voucher> readVoucher(UUID voucherId);

    List<Voucher> readVouchersByMemberId(UUID memberId);

    Optional<Voucher> updateVoucher(Voucher voucher);

    Optional<Voucher> deleteVoucher(Voucher voucher);
}
