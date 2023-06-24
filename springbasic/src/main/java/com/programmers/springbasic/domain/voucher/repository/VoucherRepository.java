package com.programmers.springbasic.domain.voucher.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository<T, ID> {
    void save(T voucher);
    List<T> findAll();
}
