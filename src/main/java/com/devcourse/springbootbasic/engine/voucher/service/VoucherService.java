package com.devcourse.springbootbasic.engine.voucher.service;

import com.devcourse.springbootbasic.engine.config.Message;
import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import com.devcourse.springbootbasic.engine.voucher.domain.VoucherDto;
import com.devcourse.springbootbasic.engine.voucher.factory.FixedVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.factory.PercentVoucherFactory;
import com.devcourse.springbootbasic.engine.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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