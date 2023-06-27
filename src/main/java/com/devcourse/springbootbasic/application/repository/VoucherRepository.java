package com.devcourse.springbootbasic.application.repository;

import com.devcourse.springbootbasic.application.domain.Voucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> insert(Voucher voucher);

    List<String> findAll();

    default void setVoucherMap(Map<UUID, Voucher> map) {}
}
