package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    void create(Voucher voucher);
    List<Voucher> list();
}
