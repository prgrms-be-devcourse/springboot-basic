package com.devcourse.springbootbasic.application.domain.voucher.service;

import com.devcourse.springbootbasic.application.global.constant.Message;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;
import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;
import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherDto;
import com.devcourse.springbootbasic.application.domain.voucher.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.application.domain.voucher.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.application.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    private Voucher convertDtoToDomain(VoucherDto voucherDto) {
        if (voucherDto.voucherType().equals(VoucherType.FIXED_AMOUNT)) {
            return new FixedVoucherFactory().create(voucherDto.discountValue());
        }
        return new PercentVoucherFactory().create(voucherDto.discountValue());
    }

    public Voucher createVoucher(VoucherDto voucherDto) {
        return voucherRepository.insert(convertDtoToDomain(voucherDto))
                .orElseThrow(() -> new InvalidDataException(Message.INAVLID_VOUCHER_INSERTION));
    }

    public List<String> getAllVouchers() {
        return voucherRepository.findAll();
    }
}