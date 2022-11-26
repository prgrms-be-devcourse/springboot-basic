package prgms.vouchermanagementapp.repository;

import prgms.vouchermanagementapp.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    List<Voucher> findAllByCustomerName();

    void updateVoucher(UUID voucherId, long fixedDiscountLevel);

    void deleteAll();
}
