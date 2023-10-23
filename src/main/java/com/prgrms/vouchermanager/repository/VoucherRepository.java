package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Voucher;

import java.util.List;

public interface VoucherRepository {
    Voucher create(Voucher voucher);
    List<Voucher> list();
}
