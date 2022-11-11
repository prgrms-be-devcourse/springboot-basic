package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.voucher.domain.Voucher;

import java.util.List;

public interface VoucherStorage {

    void save(Voucher voucher);

    List<Voucher> findAll();
}
