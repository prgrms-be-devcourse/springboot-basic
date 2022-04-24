package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;

public interface VoucherRepository {

    Voucher save(Voucher voucher);
}
