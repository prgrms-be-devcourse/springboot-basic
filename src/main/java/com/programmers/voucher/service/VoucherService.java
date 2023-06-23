package com.programmers.voucher.service;

import com.programmers.console.util.VoucherPolicy;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = toEntity(UUID.randomUUID(), voucherRequestDto);
        return voucherRepository.save(voucher);
    }


    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    private Voucher toEntity(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        return VoucherPolicy.findByCommand(voucherRequestDto.getVoucherType())
                .constructor(voucherId, voucherRequestDto.getDiscountAmount(), LocalDate.now());
    }



}
