package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("jpa")
public interface VoucherJPARepository extends JpaRepository<Voucher, Long>, VoucherRepository {

    default Optional<Voucher> saveVoucher(Voucher voucher) {
        return Optional.of(save(voucher));
    }

    default Optional<Voucher> getVoucherById(long voucherId) {
        return findById(voucherId);
    }

    default List<Voucher> getAllVouchers() {
        return findAll();
    }
}
