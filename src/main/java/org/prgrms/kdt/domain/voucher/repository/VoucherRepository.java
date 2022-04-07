package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    UUID save(Voucher voucher);

    List<Voucher> findAll();
}
