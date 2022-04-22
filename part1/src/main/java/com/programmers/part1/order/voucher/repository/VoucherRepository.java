package com.programmers.part1.order.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository<ID, T>{
    T save(T voucher);
    List<T> findAll();
    Optional<T> findById(ID voucherId);
    List<T> findVoucherByCustomerId(UUID customerId);
    T update (T voucher);
    void deleteById(ID voucherId);
    void deleteAll();
}
