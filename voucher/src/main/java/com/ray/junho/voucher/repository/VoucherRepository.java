package com.ray.junho.voucher.repository;

import com.ray.junho.voucher.domain.Voucher;

import java.util.List;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
