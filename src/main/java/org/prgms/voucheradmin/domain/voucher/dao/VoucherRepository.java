package org.prgms.voucheradmin.domain.voucher.dao;

import java.io.IOException;
import java.util.List;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher) throws IOException;

    List<Voucher> getAll() throws IOException;
}
