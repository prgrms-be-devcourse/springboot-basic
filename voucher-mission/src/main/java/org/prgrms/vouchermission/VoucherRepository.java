package org.prgrms.vouchermission;

import java.util.List;

public interface VoucherRepository {
    Voucher create(Voucher voucher);
    List<Voucher> findAll();
}
