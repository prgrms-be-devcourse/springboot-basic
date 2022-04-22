package com.prgms.management.voucher.service;

import com.prgms.management.voucher.model.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    List<Voucher> findVouchers();
    
    Voucher addVoucher(Voucher voucher);
    
    Voucher findVoucherById(UUID id);
    
    void removeVoucherById(UUID id);
}
