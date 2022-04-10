package org.prgms.management.repository;

import org.prgms.management.entity.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public interface VoucherRepository {
    boolean save(Voucher voucher);

    Map<UUID, Voucher> getAll();
}
