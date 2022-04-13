package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public void addVoucher(VoucherType voucherType, long amount) throws IllegalArgumentException {
        Voucher newVoucher = Voucher.createVoucher(voucherType, amount);
        repository.save(newVoucher);
    }

    public List<Voucher> findAllVouchers() {
        return repository.findAll();
    }

}
