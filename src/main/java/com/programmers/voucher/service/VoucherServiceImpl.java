package com.programmers.voucher.service;

import com.programmers.voucher.domain.VoucherFactory;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public UUID create(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = toEntity(voucherRequestDto);
        return voucherRepository.save(voucher);
    }

    public List<VoucherResponseDto> findVouchers() {
        return voucherRepository.findAll();
    }

    private Voucher toEntity(VoucherRequestDto requestDto) {
        return voucherFactory.createVoucher(requestDto);
    }
}
