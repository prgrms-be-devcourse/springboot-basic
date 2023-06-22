package kr.co.springbootweeklymission.domain.voucher.dao;

import kr.co.springbootweeklymission.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();
}
