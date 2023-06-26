package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Map<UUID, Voucher> findAll();

}
