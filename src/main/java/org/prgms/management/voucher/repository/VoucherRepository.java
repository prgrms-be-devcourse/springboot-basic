package org.prgms.management.voucher.repository;

import org.prgms.management.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
public interface VoucherRepository {
    boolean save(Voucher voucher);

    Map<UUID, Voucher> getAll();
}
