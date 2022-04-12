package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public void addVoucher(Voucher voucher) {
        repository.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return repository.findAll();
    }
}
