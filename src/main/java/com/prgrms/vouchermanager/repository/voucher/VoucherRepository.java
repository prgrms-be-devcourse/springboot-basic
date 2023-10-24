package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher create(Voucher voucher);
    List<Voucher> list();

    public Voucher findById(UUID id);

    void updateDiscount(UUID id, int discount);

    UUID delete(UUID id);
}
