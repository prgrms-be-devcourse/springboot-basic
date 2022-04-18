package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;

import java.util.List;

public interface VoucherRepository {

    List<Voucher> findAll();
    Voucher save(Voucher voucher);
}
