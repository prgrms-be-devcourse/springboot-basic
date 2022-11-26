package prgms.vouchermanagementapp.repository;

import prgms.vouchermanagementapp.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    void updateVoucher(UUID voucherId, long fixedDiscountLevel);

    void deleteAll();
}
