package org.prgrms.application.repository;

import org.prgrms.application.domain.Voucher;

import java.util.List;

public interface VoucherRepository {

    List<Voucher> findAll();

    Voucher insert(Voucher voucher);
}
