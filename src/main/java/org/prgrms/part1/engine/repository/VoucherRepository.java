package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}
