package com.programmers.voucher.service;

import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.menu.Message.VOUCHER_ID_NOT_FOUND;
import static com.programmers.voucher.voucher.VoucherValidator.getValidateVoucher;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository repository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Voucher register(String voucherType, String value) {
        Voucher voucher = getValidateVoucher(voucherType, value);
        return repository.registerVoucher(voucher);
    }

    @Override
    public Voucher getVoucher(UUID voucherId) {
        Optional<Voucher> result = repository.findById(voucherId);
        return result.orElseThrow(() ->
                new RuntimeException(VOUCHER_ID_NOT_FOUND.getMessage()));
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
