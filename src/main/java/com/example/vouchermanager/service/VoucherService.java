package com.example.vouchermanager.service;

import com.example.vouchermanager.console.VoucherType;
import com.example.vouchermanager.domain.FixedAmountVoucher;
import com.example.vouchermanager.domain.PercentAmountVoucher;
import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.repository.VoucherMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    VoucherMemoryRepository repository;

    @Autowired
    public VoucherService(VoucherMemoryRepository repository) {
        this.repository = repository;
    }

    public void create(VoucherType voucherType, long discount) {
        Voucher voucher = null;
        if(voucherType == VoucherType.FIXED) {
            voucher = new FixedAmountVoucher(discount);
        } else if(voucherType == VoucherType.PERCENT) {
            voucher = new PercentAmountVoucher(discount);
        }

        repository.create(voucher);
    }
    public List<Voucher> list() {
        return repository.list();
    }
}
