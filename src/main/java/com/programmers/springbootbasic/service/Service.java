package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import com.programmers.springbootbasic.repository.Repository;
import com.programmers.springbootbasic.util.UuidProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Service {
    private final Repository repository;
    private final UuidProvider uuidProvider;

    @Autowired
    public Service(Repository repository, UuidProvider uuidProvider) {
        this.repository = repository;
        this.uuidProvider = uuidProvider;
    }

    public void create(VoucherType voucherType, Long amount) {
        Voucher voucher = voucherType.createVoucher(uuidProvider.generateUUID(), amount);
        repository.save(voucher);
    }

    public List<Voucher> getAll() {
        return repository.findAll();
    }
}
