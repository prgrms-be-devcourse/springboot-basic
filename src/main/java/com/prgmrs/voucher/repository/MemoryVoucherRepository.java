package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryVoucherRepository {
    Voucher save(Voucher voucher);

}
