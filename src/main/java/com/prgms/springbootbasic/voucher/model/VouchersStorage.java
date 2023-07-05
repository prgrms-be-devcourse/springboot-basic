package com.prgms.springbootbasic.voucher.model;

import java.util.List;

public interface VouchersStorage {

    void save(Voucher voucher);
    List<Voucher> findAll();

}
