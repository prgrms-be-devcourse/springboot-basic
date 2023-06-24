package com.dev.bootbasic.voucher.service;


import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherFactory;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherCreateRequest request) {
        Voucher createdVoucher = VoucherFactory.createVoucher(request);
        return voucherRepository.saveVoucher(createdVoucher);
    }

}
