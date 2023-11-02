package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.dto.voucher.request.CreateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.GetVouchersRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.UpdateVoucherRequestDto;
import com.programmers.vouchermanagement.dto.voucher.response.VoucherResponseDto;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public UUID createVoucher(CreateVoucherRequestDto request) {
        Voucher voucher = VoucherFactory.create(request.getType(), request.getAmount());
        return voucherRepository.save(voucher);
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> getVouchers(GetVouchersRequestDto request) {
        List<Voucher> vouchers = voucherRepository.findAll(request);
        return vouchers.stream()
                .map(voucher -> VoucherResponseDto.from(voucher.getId(), voucher.getType(), voucher.getAmount(), voucher.getCreatedAt()))
                .toList();
    }

    @Transactional(readOnly = true)
    public VoucherResponseDto getVoucher(UUID id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found voucher"));
        return VoucherResponseDto.from(voucher.getId(), voucher.getType(), voucher.getAmount(), voucher.getCreatedAt());
    }

    public void updateVoucher(UUID id, UpdateVoucherRequestDto request) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found voucher"));
        voucherRepository.update(Voucher.from(voucher.getId(), voucher.getType(), request.getAmount(), voucher.getCreatedAt()));
    }

    public void deleteVoucher(UUID id) {
        voucherRepository.deleteById(id);
    }
}
