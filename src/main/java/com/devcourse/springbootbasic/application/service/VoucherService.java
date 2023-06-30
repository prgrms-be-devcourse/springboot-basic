package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.util.converter.DtoConverter;
import com.devcourse.springbootbasic.application.util.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.application.util.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.application.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherDto voucherDto) {
        return voucherRepository.insert(DtoConverter.convertDtoToDomain(voucherDto))
                .orElseThrow(() -> new InvalidDataException(Message.INAVLID_VOUCHER_INSERTION.getMessageText()));
    }

    public List<String> getAllVouchers() {
        return voucherRepository.findAll();
    }
}