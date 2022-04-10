package com.prgrms.vouchermanagement.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    void save(Voucher voucher);

    List<Voucher> findAll();
}
