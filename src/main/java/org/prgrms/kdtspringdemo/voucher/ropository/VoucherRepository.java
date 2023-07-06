package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
