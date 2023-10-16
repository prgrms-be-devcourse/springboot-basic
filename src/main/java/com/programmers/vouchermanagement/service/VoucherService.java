package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.Voucher;
import com.programmers.vouchermanagement.domain.VoucherFactory;
import com.programmers.vouchermanagement.domain.VoucherType;
import com.programmers.vouchermanagement.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;


    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(String voucherName, float discountAmount, VoucherType voucherType) {
        Voucher voucher = VoucherFactory.createVoucher(voucherName, discountAmount, voucherType);
        return voucherRepository.save(voucher);
    }

    public List<Voucher> voucherList() {
        return voucherRepository.findAll();

    }
}
