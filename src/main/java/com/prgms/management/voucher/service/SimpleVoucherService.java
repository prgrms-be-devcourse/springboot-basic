package com.prgms.management.voucher.service;

import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SimpleVoucherService implements VoucherService {
    private final VoucherRepository voucherRepository;
    
    public SimpleVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    
    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }
    
    public Voucher addVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }
    
    @Override
    public Voucher findVoucherById(UUID id) {
        return voucherRepository.findById(id);
    }
    
    @Override
    public void removeVoucherById(UUID id) {
        voucherRepository.removeById(id);
    }
}
