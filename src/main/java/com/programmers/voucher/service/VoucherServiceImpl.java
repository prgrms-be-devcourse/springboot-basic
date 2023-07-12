package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherMapper;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponseDto create(VoucherRequestDto requestDto) {
        Voucher voucher = VoucherMapper.convertRequestDtoToDomain(requestDto);
        voucherRepository.save(voucher);
        return VoucherMapper.convertDomainToResponseDto(voucher);
    }

    public List<VoucherResponseDto> findVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherMapper::convertDomainToResponseDto)
                .toList();
    }

    public VoucherResponseDto findVoucher(UUID voucherID) {
        Voucher voucher = voucherRepository.findById(voucherID);
        return VoucherMapper.convertDomainToResponseDto(voucher);
    }
}
