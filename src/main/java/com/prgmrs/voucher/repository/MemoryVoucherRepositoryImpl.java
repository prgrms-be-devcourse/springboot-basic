package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepositoryImpl implements MemoryVoucherRepository {
    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }
}
