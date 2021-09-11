package org.prgms.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId); //Optional : VoucherId가 없을 수도 있다.

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> findByVoucherType(VoucherType voucherType);

}
