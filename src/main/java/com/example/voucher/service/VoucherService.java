package com.example.voucher.service;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDto;
import com.example.voucher.repository.VoucherRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createVoucher(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.toVoucher();
        voucherRepository.insert(voucher);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
