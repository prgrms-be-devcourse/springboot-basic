package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findVouchersByMemberId(UUID memberId);

    List<Voucher> findAll();

    void update(Voucher voucher);

    void deleteById(UUID voucherId);

    void deleteVoucherByVoucherIdAndMemberId(UUID voucherId, UUID memberId);
}
