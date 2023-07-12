package org.devcourse.springbasic.domain.voucher.service;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.voucher.dao.VoucherRepository;
import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public UUID save(VoucherDto.SaveRequestDto voucherDto) {
        VoucherType voucherType = voucherDto.getVoucherType();
        Voucher voucher = voucherType.getVoucherSupplier().get();
        return voucherRepository.save(voucher);
    }

    public List<VoucherDto.ResponseDto> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(voucher -> new VoucherDto.ResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountRate()))
                .collect(Collectors.toList());
    }

    public VoucherDto.ResponseDto findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId);
        return new VoucherDto.ResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountRate());
    }
}