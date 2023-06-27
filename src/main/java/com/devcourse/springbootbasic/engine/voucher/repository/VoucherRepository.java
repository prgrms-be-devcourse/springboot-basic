package com.devcourse.springbootbasic.engine.voucher.repository;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
// standard interface
public interface VoucherRepository {
    Optional<Voucher> insert(Voucher voucher);

    List<String> findAll();

    default void setVoucherMap(Map<UUID, Voucher> map) {}
}
