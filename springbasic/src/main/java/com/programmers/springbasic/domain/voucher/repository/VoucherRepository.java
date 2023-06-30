package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherOption;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository {
    void save(Voucher voucher);
    List<Voucher> findAll();
    List<Voucher> findAllByVoucherType(VoucherOption voucherOption);
}
