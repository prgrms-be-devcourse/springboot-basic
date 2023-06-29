package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.application.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.application.repository.voucher.VoucherRepository;
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
                .orElseThrow(() -> new InvalidDataException(Message.INAVLID_VOUCHER_INSERTION.getMessageText()));
    }

    public List<String> getAllVouchers() {
        return voucherRepository.findAll();
    }
}