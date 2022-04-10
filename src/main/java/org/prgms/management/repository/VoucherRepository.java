package org.prgms.management.repository;

import org.prgms.management.entity.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    Map<UUID, Voucher> getAll();
}
