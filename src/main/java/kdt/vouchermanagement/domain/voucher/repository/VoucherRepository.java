package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    void deleteById(Long voucherId);

    Optional<Voucher> findById(Long voucherId);
}
