package com.programmers.VoucherManagementApplication.service;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.repository.VoucherRepository;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherType voucherType, Amount amount) {
        return voucherRepository.addVoucher(voucherType.createVoucher(voucherType, amount));
    }

    public Map<UUID, Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
