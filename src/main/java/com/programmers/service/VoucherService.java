package com.programmers.service;

import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import com.programmers.domain.voucher.dto.VoucherCreateRequestDto;
import com.programmers.domain.voucher.dto.VoucherResponseDto;
import com.programmers.domain.voucher.dto.VoucherUpdateRequestDto;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
import com.programmers.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponseDto save(VoucherCreateRequestDto voucherDto) {
        Voucher voucher = voucherRepository.save(VoucherType.createVoucher(voucherDto.type().toString(), voucherDto.name(), voucherDto.amount()));

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType());
    }

    public VouchersResponseDto findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        return new VouchersResponseDto(vouchers);
    }

    public VoucherResponseDto findById(UUID id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);

        return new VoucherResponseDto(voucher.get().getVoucherId(), voucher.get().getVoucherName(), voucher.get().getVoucherValue(), voucher.get().getVoucherType());
    }

    @Transactional
    public VoucherResponseDto update(VoucherUpdateRequestDto voucherDto) {
        Voucher voucher = voucherRepository.update(VoucherType.createVoucher(voucherDto.type().toString(), voucherDto.id(), voucherDto.name(), voucherDto.amount()));

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType());
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
