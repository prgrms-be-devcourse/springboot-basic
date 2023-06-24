package org.prgms.voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher create();

    List<Voucher> findAll();
}
