package org.prgrms.kdtspringvoucher.voucher.repository;

import org.prgrms.kdtspringvoucher.voucher.service.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher save(Voucher voucher) throws IOException;

}
