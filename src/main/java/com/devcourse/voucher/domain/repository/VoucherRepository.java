package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
