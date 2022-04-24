package com.prgms.management.voucher.repository;

import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher findById(UUID voucherId);
    
    List<Voucher> findByType(VoucherType type);
    
    List<Voucher> findByDate(Timestamp start, Timestamp end);
    
    List<Voucher> findByTypeAndDate(VoucherType type, Timestamp start, Timestamp end);
    
    List<Voucher> findAll();
    
    Voucher save(Voucher voucher);
    
    void removeById(UUID voucherId);
}
