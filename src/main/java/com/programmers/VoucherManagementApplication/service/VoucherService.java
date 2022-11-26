package com.programmers.VoucherManagementApplication.service;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.repository.VoucherRepository;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherType voucherType, Amount amount) {
        return voucherRepository.insert(voucherType.createVoucher(voucherType, amount));
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
