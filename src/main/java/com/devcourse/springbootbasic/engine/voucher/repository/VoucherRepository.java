package com.devcourse.springbootbasic.engine.voucher.repository;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;

import java.util.List;
import java.util.Map;
import java.util.UUID;
// standard interface
public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    <T> List<T> findAll();

    void setVoucherMap(Map<UUID, Voucher> map);
}
