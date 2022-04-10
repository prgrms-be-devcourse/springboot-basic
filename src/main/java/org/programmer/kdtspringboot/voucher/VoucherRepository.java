package org.programmer.kdtspringboot.voucher;

import java.io.IOException;
import java.util.List;

public interface VoucherRepository {
    void insert(Voucher voucher);
    List<Voucher> findAll() throws IOException;
}
