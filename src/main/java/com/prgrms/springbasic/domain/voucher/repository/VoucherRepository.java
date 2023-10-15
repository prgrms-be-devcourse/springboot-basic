package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher saveVoucher(Voucher voucher);
    List<Voucher> findAll();
}
