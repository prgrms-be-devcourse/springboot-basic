package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;

import java.util.List;

public class BasicVoucherService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public BasicVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher create(String name, Voucher.type type) {
        return voucherRepository.save(name, type);
    }

    @Override
    public List<Voucher> listAll() {
        return voucherRepository.listAll();
    }
}
