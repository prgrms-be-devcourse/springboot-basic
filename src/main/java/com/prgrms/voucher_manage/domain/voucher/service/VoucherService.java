package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VoucherService{
    private final VoucherRepository voucherRepository;

    public void createVoucher(CreateVoucherDto dto) {
        voucherRepository.save(dto.of());
    }

    public List<Voucher> getVouchers() {
        return  voucherRepository.findAll();
    }
}
