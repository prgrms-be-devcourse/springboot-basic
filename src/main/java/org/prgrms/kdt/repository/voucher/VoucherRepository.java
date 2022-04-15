package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}
