package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher create(Voucher voucher);
    List<Voucher> list();

    Voucher findById(UUID id);

    Voucher updateDiscount(Voucher updateVoucher);

    int delete(UUID id);
}
