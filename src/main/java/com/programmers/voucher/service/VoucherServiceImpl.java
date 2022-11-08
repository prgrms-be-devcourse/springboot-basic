package com.programmers.voucher.service;

import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository repository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Voucher register(Voucher voucher) {
        return repository.registerVoucher(voucher);
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        Optional<Voucher> result = repository.findById(voucherId);
        return result.orElseThrow(() ->
                new RuntimeException("존재하지 않는 바우처입니다."));
    }

    @Override
    public List<Voucher> findAll() {
        return repository.findAllVouchers();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
