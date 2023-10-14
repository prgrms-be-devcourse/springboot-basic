package com.example.vouchermanager.service;

import com.example.vouchermanager.console.VoucherType;
import com.example.vouchermanager.domain.FixedAmountVoucher;
import com.example.vouchermanager.domain.PercentAmountVoucher;
import com.example.vouchermanager.domain.Voucher;
import com.example.vouchermanager.domain.VoucherInfo;
import com.example.vouchermanager.repository.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    MemoryRepository repository;

    @Autowired
    public VoucherService(MemoryRepository repository) {
        this.repository = repository;
    }

    public void create(VoucherInfo voucherInfo) {
        Voucher voucher = null;
        long beforeDiscount = voucherInfo.getAmount();
        VoucherType voucherType = voucherInfo.getVoucherType();
        if(voucherType == VoucherType.FIXED) {
            voucher = new FixedAmountVoucher(beforeDiscount);
        } else if(voucherType == VoucherType.PERCENT) {
            voucher = new PercentAmountVoucher(beforeDiscount);
        }
        repository.create(voucher);
    }
    public List<Voucher> list() {
        return repository.list();
    }
}
