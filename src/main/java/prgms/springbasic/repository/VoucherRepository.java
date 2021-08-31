package prgms.springbasic.repository;


import prgms.springbasic.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher save(Voucher voucher);

    List<Voucher> getVoucherList();
}
