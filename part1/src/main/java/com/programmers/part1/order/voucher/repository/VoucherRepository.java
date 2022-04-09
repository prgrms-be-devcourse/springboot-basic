package com.programmers.part1.order.voucher.repository;

import java.util.List;

public interface VoucherRepository<ID, T>{
    T save(T voucher);
    List<T> findAll();
}
