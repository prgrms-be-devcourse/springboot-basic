package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    boolean insert(Voucher voucher);

    default Optional<Voucher> findById(UUID voucherId){
        return Optional.empty();
    }

    List<Voucher> findAll();

    default boolean update(Voucher voucher){
        return false;
    }

    default boolean deleteById(UUID voucherId){
        return false;
    }

}
