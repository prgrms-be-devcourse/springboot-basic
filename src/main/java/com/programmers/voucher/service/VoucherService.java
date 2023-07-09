package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherCreateRequestDto;
import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherDto save(VoucherCreateRequestDto voucherCreateRequestDto) {
        Voucher requestVoucher = VoucherType.createVoucher(voucherCreateRequestDto.type().toString(), voucherCreateRequestDto.name(), voucherCreateRequestDto.value());
        Voucher voucher = voucherRepository.save(requestVoucher);

        return new VoucherDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType(), voucher.getCustomerId());
    }

    public VouchersResponseDto findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        return new VouchersResponseDto(vouchers);
    }

    public VoucherDto findById(UUID id) {
        Voucher voucher = voucherRepository.findById(id).get();

        return new VoucherDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType(), voucher.getCustomerId());
    }

    @Transactional
    public VoucherDto update(VoucherDto voucherDto) {
        Voucher requestVoucher = VoucherType.createVoucher(voucherDto.type().toString(), voucherDto.id(), voucherDto.name(), voucherDto.value());
        Voucher voucher = voucherRepository.update(requestVoucher);

        return new VoucherDto(voucher.getVoucherId(), voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType(), voucher.getCustomerId());
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
