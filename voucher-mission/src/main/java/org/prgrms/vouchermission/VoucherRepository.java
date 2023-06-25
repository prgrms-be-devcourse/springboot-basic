package org.prgrms.vouchermission;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}
