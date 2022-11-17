package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("jpa")
public interface VoucherJPARepository extends JpaRepository<Voucher, UUID>, VoucherRepository {

    default boolean saveVoucher(Voucher voucher) {
        Voucher saveVoucher = save(voucher);
        return saveVoucher.getVoucherId().equals(voucher.getVoucherId());
    }

    default Optional<Voucher> getVoucherById(UUID voucherId) {
        return findById(voucherId);
    }

    default List<Voucher> getAllVouchers() {
        return findAll();
    }
}
