package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher create(Voucher voucher);
    List<Voucher> list();

    void updateDiscount(UUID id, int discount);

    UUID delete(UUID id);
}
