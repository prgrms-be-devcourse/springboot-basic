package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Voucher;

import java.util.UUID;

public interface VoucherRepository {
    void insert(Voucher voucher);
    Voucher findById(UUID voucherId);
    void showAll();


}
