package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public Voucher addVoucher(VoucherType voucherType, long amount) throws IllegalArgumentException {
        Voucher newVoucher = voucherType.constructor(UUID.randomUUID(), amount, LocalDateTime.now());
        repository.save(newVoucher);
        log.info("voucher is saved - {}", newVoucher);
        return newVoucher;
    }

    public List<Voucher> findAllVouchers() {
        List<Voucher> allVouchers = repository.findAll();
        log.info("find all vouchers. size={}", allVouchers.size());
        return allVouchers;
    }
}
