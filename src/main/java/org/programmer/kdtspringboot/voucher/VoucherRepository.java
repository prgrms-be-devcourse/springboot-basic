package org.programmer.kdtspringboot.voucher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {
    void insert(Voucher voucher);
    List<Voucher> findAll();
}
