package com.programmers.springbootbasic.domain.voucher.service;

import com.programmers.springbootbasic.domain.voucher.dto.VoucherRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = VoucherType.of(voucherRequestDto.getVoucherType(), UUID.randomUUID(), voucherRequestDto.getValue());
        return voucherRepository.save(voucher);
    }
}
