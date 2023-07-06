package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.Voucher;

import java.util.List;

public interface VouchersStorage {

    void save(Voucher voucher);
    List<Voucher> findAll();

}
