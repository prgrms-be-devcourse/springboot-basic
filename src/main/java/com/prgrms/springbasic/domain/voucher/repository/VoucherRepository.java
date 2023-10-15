package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository {

    Voucher saveVoucher(Voucher voucher);

    List<Voucher> findAll();
}
