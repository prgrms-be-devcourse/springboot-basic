package com.pppp0722.vouchermanagement.voucher.repository;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByMemberId(UUID memberId);

    Voucher update(Voucher voucher);

    Voucher delete(Voucher voucher);

    void deleteAll();
}
