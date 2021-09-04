package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    void create(int type);
    List<Voucher> list();
    List<Voucher> vouchers = new ArrayList<>();
}
