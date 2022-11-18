package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> insert(Voucher voucher);

    default Optional<Voucher> findById(UUID voucherId){
        return Optional.empty();
    }

    List<Voucher> findAll();

    default Optional<Voucher> update(Voucher voucher){
        return Optional.empty();
    }

    default boolean deleteById(UUID voucherId){
        return false;
    }

}
