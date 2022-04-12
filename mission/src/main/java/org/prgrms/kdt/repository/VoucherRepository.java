package org.prgrms.kdt.repository;

import org.prgrms.kdt.models.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
