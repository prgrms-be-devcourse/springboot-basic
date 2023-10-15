package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);
    List<Voucher> findAllVouchers();
}
