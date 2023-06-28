package org.prgrms.application.repository;

import org.prgrms.application.domain.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {

    Map<UUID, Voucher> findAll();

    Voucher insert(Voucher voucher);
}
