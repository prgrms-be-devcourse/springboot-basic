package com.prgms.missionW3D2.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> voucherList();
    void insert(Voucher voucher);
}
