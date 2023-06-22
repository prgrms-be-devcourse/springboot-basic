package com.devcourse.springbootbasic.engine.voucher.repository;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
    void setVoucherMap(Map<UUID, Voucher> map);
}
