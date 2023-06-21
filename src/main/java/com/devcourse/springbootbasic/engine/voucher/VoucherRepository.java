package com.devcourse.springbootbasic.engine.voucher;

import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}
