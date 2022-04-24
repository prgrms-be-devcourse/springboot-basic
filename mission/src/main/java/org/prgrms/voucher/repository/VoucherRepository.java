package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
