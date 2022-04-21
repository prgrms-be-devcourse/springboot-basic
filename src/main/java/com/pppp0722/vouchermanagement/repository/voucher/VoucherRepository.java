package com.pppp0722.vouchermanagement.repository.voucher;

import com.pppp0722.vouchermanagement.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher createVoucher(Voucher voucher);

    List<Voucher> readVouchers();

    Optional<Voucher> readVoucher(UUID voucherId);

    Voucher updateVoucher(Voucher voucher);

    Voucher deleteVoucher(Voucher voucher);
}
