package com.pppp0722.vouchermanagement.voucher.repository;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByMemberId(UUID memberId);

    Optional<Voucher> update(Voucher voucher);

    Optional<Voucher> delete(Voucher voucher);

    void deleteAll();
}
