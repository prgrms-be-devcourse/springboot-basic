package org.voucherProject.voucherProject.service.voucher;

import org.voucherProject.voucherProject.entity.voucher.Voucher;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher getVoucher(UUID voucherId) throws IOException;

    List<Voucher> findAll() throws IOException;

    Voucher save(Voucher voucher) throws IOException;

}
